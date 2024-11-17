package com.seungilahn.application.port.out

import com.seungilahn.domain.PieceAssignment

interface PieceAssignmentRepository {
    fun findAllByPieceId(pieceId: Long): List<PieceAssignment>
    fun saveAll(pieceAssignments: List<PieceAssignment>): List<PieceAssignment>
}