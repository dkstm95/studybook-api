package com.seungilahn.domain

data class PieceAnalysis(
    private val piece: Piece,
    private val problems: List<Problem>,
    private val grades: List<StudentProblemGrade>
) {

    fun getStudentAnalytics(): List<StudentAnalytics> {
        return grades.groupBy { it.studentId }
            .map { (studentId, studentGrades) ->
                StudentAnalytics(
                    studentId = studentId,
                    correctRate = calculateCorrectRate(studentGrades)
                )
            }
    }

    fun getProblemAnalytics(): List<ProblemAnalytics> {
        return problems.map { problem ->
            val problemGrades = grades.filter { it.problemId == problem.id }
            ProblemAnalytics(
                problemId = problem.id!!,
                correctRate = calculateCorrectRate(problemGrades)
            )
        }
    }

    fun getPieceId(): Long {
        return piece.id!!
    }

    fun getPieceName(): String {
        return piece.name
    }

    private fun calculateCorrectRate(grades: List<StudentProblemGrade>): Double {
        if (grades.isEmpty()) return 0.0
        return grades.count { it.isCorrect }.toDouble() / grades.size
    }

    data class StudentAnalytics(
        val studentId: Long,
        val correctRate: Double
    )

    data class ProblemAnalytics(
        val problemId: Long,
        val correctRate: Double
    )

}
