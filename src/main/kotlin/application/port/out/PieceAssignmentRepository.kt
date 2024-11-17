package com.seungilahn.application.port.out

import com.seungilahn.domain.PieceAssignment

interface PieceAssignmentRepository {
    fun findByPieceIdAndStudentId(pieceId: Long, studentId: Long): PieceAssignment?
    fun findAllByPieceId(pieceId: Long): List<PieceAssignment>
    fun saveAll(pieceAssignments: List<PieceAssignment>): List<PieceAssignment>
}