package com.seungilahn.application.port.`in`

import com.seungilahn.application.port.`in`.response.AnalyzePieceResponse
import com.seungilahn.application.port.`in`.response.GetPieceProblemResponse
import com.seungilahn.application.port.`in`.response.GetProblemResponse
import com.seungilahn.application.port.out.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PieceQueryService(
    private val problemRepository: ProblemRepository,
    private val pieceRepository: PieceRepository,
    private val pieceAssignmentRepository: PieceAssignmentRepository,
    private val pieceProblemRepository: PieceProblemRepository,
    private val studentProblemGradeRepository: StudentProblemGradeRepository,
) {

    fun getPieceProblems(pieceId: Long, studentId: Long): GetPieceProblemResponse {
        val assignment = pieceAssignmentRepository.findByPieceIdAndStudentId(pieceId, studentId)
            ?: throw IllegalArgumentException("해당 학습지가 배정되지 않았습니다.")

        val pieceProblems = pieceProblemRepository.findAllByPieceId(assignment.piece.id!!)

        val problems = problemRepository.fetchAllById(
            pieceProblems.map { it.problemId }
        )

        return GetPieceProblemResponse(
            pieceId = assignment.piece.id!!,
            problems = problems.map { GetProblemResponse.from(it) }
        )
    }

    fun analyzePiece(pieceId: Long): AnalyzePieceResponse {
        val piece = pieceRepository.findByIdOrNull(pieceId)
            ?: throw IllegalArgumentException("해당 학습지가 존재하지 않습니다.")

        val pieceProblems = pieceProblemRepository.findAllByPieceId(piece.id!!)

        val problems = problemRepository.fetchAllById(
            pieceProblems.map { it.problemId }
        )

        val studentGrades = studentProblemGradeRepository.findAllByPieceId(piece.id!!)

        val studentData = studentGrades.groupBy { it.studentId }.map { (studentId, grades) ->
            val correctCount = grades.count { it.isCorrect }
            val totalCount = grades.size
            val correctRate = if (totalCount > 0) correctCount.toDouble() / totalCount else 0.0

            AnalyzePieceResponse.StudentData(
                studentId = studentId,
                correctRate = correctRate,
            )
        }

        val problemStatistics = problems.map { problem ->
            val gradesForProblem = studentGrades.filter { it.problemId == problem.id }
            val correctCount = gradesForProblem.count { it.isCorrect }
            val totalCount = gradesForProblem.size
            val correctRate = if (totalCount > 0) correctCount.toDouble() / totalCount else 0.0

            AnalyzePieceResponse.ProblemStatistics(
                problemId = problem.id!!,
                correctRate = correctRate,
            )
        }

        return AnalyzePieceResponse(
            pieceId = piece.id!!,
            pieceName = piece.name,
            students = studentData,
            problemStatistics = problemStatistics,
        )
    }

}
