package com.seungilahn.domain

import com.seungilahn.application.port.`in`.response.AnalyzePieceResponse

data class PieceAnalysis(
    private val piece: Piece,
    private val problems: List<Problem>,
    private val grades: List<StudentProblemGrade>
) {

    fun generateResponse(): AnalyzePieceResponse {
        return AnalyzePieceResponse(
            pieceId = piece.id!!,
            pieceName = piece.name,
            students = analyzeStudents(),
            problemStatistics = analyzeProblems()
        )
    }

    private fun analyzeStudents(): List<AnalyzePieceResponse.StudentData> {
        return grades.groupBy { it.studentId }
            .map { (studentId, studentGrades) ->
                AnalyzePieceResponse.StudentData(
                    studentId = studentId,
                    correctRate = calculateCorrectRate(studentGrades)
                )
            }
    }

    private fun analyzeProblems(): List<AnalyzePieceResponse.ProblemStatistics> {
        return problems.map { problem ->
            val problemGrades = grades.filter { it.problemId == problem.id }
            AnalyzePieceResponse.ProblemStatistics(
                problemId = problem.id!!,
                correctRate = calculateCorrectRate(problemGrades)
            )
        }
    }

    private fun calculateCorrectRate(grades: List<StudentProblemGrade>): Double {
        if (grades.isEmpty()) return 0.0
        return grades.count { it.isCorrect }.toDouble() / grades.size
    }

}
