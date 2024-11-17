package com.seungilahn.application.port.`in`

import com.seungilahn.adapter.`in`.web.GetProblemResponse
import com.seungilahn.application.port.out.PieceProblemRepository
import com.seungilahn.application.port.out.PieceRepository
import com.seungilahn.application.port.out.ProblemRepository
import com.seungilahn.domain.Piece
import com.seungilahn.domain.PieceProblem
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PieceService(
    private val problemRepository: ProblemRepository,
    private val pieceRepository: PieceRepository,
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

}