package com.seungilahn.application.port.out

import com.seungilahn.domain.Piece

interface PieceRepository {
    fun save(piece: Piece): Piece
}
