package com.seungilahn.application.port.out

import com.seungilahn.domain.Piece

interface PieceRepository {
    fun findByIdOrNull(pieceId: Long): Piece?
    fun save(piece: Piece): Piece
}
