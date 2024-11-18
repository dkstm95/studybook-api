package com.seungilahn.application.port.out

import com.seungilahn.domain.Piece
import com.seungilahn.domain.PieceAssignment

interface PieceRepository {
    fun findPieceById(pieceId: Long): Piece?
    fun savePiece(piece: Piece): Piece

    fun findAssignmentByPieceIdAndStudentId(pieceId: Long, studentId: Long): PieceAssignment?
    fun findAllAssignmentByPieceId(pieceId: Long): List<PieceAssignment>
    fun saveAllAssignment(pieceAssignments: List<PieceAssignment>): List<PieceAssignment>
}
