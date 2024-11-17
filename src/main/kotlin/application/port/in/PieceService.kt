package com.seungilahn.application.port.`in`

import com.seungilahn.adapter.`in`.web.GetProblemResponse
import com.seungilahn.application.port.out.PieceAssignmentRepository
import com.seungilahn.application.port.out.PieceProblemRepository
import com.seungilahn.application.port.out.PieceRepository
import com.seungilahn.application.port.out.ProblemRepository
import com.seungilahn.domain.Piece
import com.seungilahn.domain.PieceAssignment
import com.seungilahn.domain.PieceProblem
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PieceService(
    private val problemRepository: ProblemRepository,
    private val pieceRepository: PieceRepository,
    private val pieceAssignmentRepository: PieceAssignmentRepository,
    private val pieceProblemRepository: PieceProblemRepository,
) {

    companion object {
        private const val MAX_PIECE_PROBLEMS_COUNT = 50
    }

    @Transactional
    fun create(teacherId: Long, pieceName: String, problemIds: List<Long>): CreatePieceResponse {
        if (problemIds.isEmpty() || problemIds.size > MAX_PIECE_PROBLEMS_COUNT) {
            throw IllegalArgumentException("문제는 1개 이상 ${MAX_PIECE_PROBLEMS_COUNT}개 이하로 선택해주세요.")
        }

        val newPiece = pieceRepository.save(
            Piece.withoutId(teacherId = teacherId, name = pieceName)
        )

        val problems = problemRepository.findAllById(problemIds)
        pieceProblemRepository.saveAll(
            problems.map { PieceProblem.withoutId(piece = newPiece, problem = it) }
        )

        return CreatePieceResponse(
            pieceId = newPiece.id!!,
            pieceName = newPiece.name,
            teacherId = newPiece.teacherId,
            problems = problems.map { GetProblemResponse.from(it) }
        )
    }

    @Transactional
    fun assign(pieceId: Long, studentIds: List<Long>, teacherId: Long): AssignPieceResponse {
        val piece = pieceRepository.findByIdOrNull(pieceId)
            ?: throw IllegalArgumentException("존재하지 않는 학습지입니다.")

        if (!piece.isSameTeacher(teacherId)) {
            throw IllegalArgumentException("본인이 생성한 학습지만 배정할 수 있습니다.")
        }

        val assignedStudentIds = pieceAssignmentRepository.findAllByPieceId(pieceId).map { it.studentId }
        val unassignedStudentIds = studentIds.filter { it !in assignedStudentIds }

        pieceAssignmentRepository.saveAll(
            unassignedStudentIds.map { PieceAssignment.withoutId(studentId = it, piece = piece) }
        )

        return AssignPieceResponse(
            pieceId = piece.id!!,
            assignedStudentsIds = studentIds,
        )
    }

}
