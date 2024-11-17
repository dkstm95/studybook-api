package com.seungilahn.application.port.`in`

import com.seungilahn.adapter.`in`.web.GetProblemResponse
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

}
