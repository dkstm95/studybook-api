package com.seungilahn.adapter.out.persistence

import com.seungilahn.domain.PieceAssignment
import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataPieceAssignmentRepository : JpaRepository<PieceAssignment, Long> {
    fun findAllByPieceId(pieceId: Long): List<PieceAssignment>
}