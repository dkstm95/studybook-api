package com.seungilahn.adapter.out.persistence

import com.seungilahn.domain.PieceProblem
import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataPieceProblemRepository : JpaRepository<PieceProblem, Long> {
    fun findAllByPieceId(pieceId: Long): List<PieceProblem>
}