package com.seungilahn.adapter.out.persistence

import com.seungilahn.domain.Piece
import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataPieceRepository : JpaRepository<Piece, Long> {
}