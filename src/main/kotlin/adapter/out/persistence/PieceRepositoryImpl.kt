package com.seungilahn.adapter.out.persistence

import com.seungilahn.application.port.out.PieceRepository
import com.seungilahn.domain.Piece
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class PieceRepositoryImpl(
    private val jpaRepository: SpringDataPieceRepository,
) : PieceRepository {

    override fun findByIdOrNull(pieceId: Long): Piece? {
        return jpaRepository.findByIdOrNull(pieceId)
    }

    override fun save(piece: Piece): Piece {
        return jpaRepository.save(piece)
    }

}