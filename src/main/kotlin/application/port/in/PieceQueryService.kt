package com.seungilahn.application.port.`in`

import com.seungilahn.application.port.`in`.response.AnalyzePieceResponse
import com.seungilahn.application.port.`in`.response.GetPieceProblemResponse
import com.seungilahn.application.port.`in`.response.GetProblemResponse
import com.seungilahn.application.port.out.PieceProblemRepository
import com.seungilahn.application.port.out.PieceRepository
import com.seungilahn.application.port.out.ProblemRepository
import com.seungilahn.application.port.out.StudentProblemGradeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PieceQueryService(
    private val problemRepository: ProblemRepository,
    private val pieceRepository: PieceRepository,
    private val pieceProblemRepository: PieceProblemRepository,
    private val studentProblemGradeRepository: StudentProblemGradeRepository,
) {

    fun getPieceProblems(pieceId: Long, studentId: Long): GetPieceProblemResponse {
        val assignment = pieceRepository.findAssignmentByPieceIdAndStudentId(pieceId, studentId)
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
        val piece = pieceRepository.findPieceById(pieceId)
            ?: throw IllegalArgumentException("해당 학습지가 존재하지 않습니다.")

        val pieceProblems = pieceProblemRepository.findAllByPieceId(piece.id!!)

        val problems = problemRepository.fetchAllById(
            pieceProblems.map { it.problemId }
        )

        val studentGrades = studentProblemGradeRepository.findAllByPieceId(piece.id!!)

        return piece.analyze(problems, studentGrades).generateResponse()
    }

}
