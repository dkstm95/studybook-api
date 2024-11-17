package com.seungilahn.adapter.out.persistence

import com.seungilahn.application.port.out.PieceProblemRepository
import com.seungilahn.application.port.out.PieceRepository
import com.seungilahn.domain.Piece
import com.seungilahn.domain.PieceProblem
import org.springframework.stereotype.Repository

@Repository
class PieceRepositoryImpl(
    private val pieceJpaRepository: SpringDataPieceRepository,
    private val pieceProblemJpaRepository: SpringDataPieceProblemRepository
) : PieceRepository, PieceProblemRepository {

    override fun save(piece: Piece): Piece {
        return pieceJpaRepository.save(piece)
    }

    override fun saveAll(pieceProblems: List<PieceProblem>) {
        pieceProblemJpaRepository.saveAll(pieceProblems)
    }

}