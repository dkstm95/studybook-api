package com.seungilahn.adapter.out.persistence

import com.seungilahn.application.port.out.PieceRepository
import com.seungilahn.domain.Piece
import com.seungilahn.domain.PieceAssignment
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class PieceRepositoryImpl(
    private val pieceJpaRepository: SpringDataPieceRepository,
    private val assignmentJpaRepository: SpringDataPieceAssignmentRepository,
) : PieceRepository {

    override fun findPieceById(pieceId: Long): Piece? {
        return pieceJpaRepository.findByIdOrNull(pieceId)
    }

    override fun savePiece(piece: Piece): Piece {
        return pieceJpaRepository.save(piece)
    }

    override fun findAssignmentByPieceIdAndStudentId(pieceId: Long, studentId: Long): PieceAssignment? {
        return assignmentJpaRepository.findByPieceIdAndStudentId(pieceId, studentId)
    }

    override fun findAllAssignmentByPieceId(pieceId: Long): List<PieceAssignment> {
        return assignmentJpaRepository.findAllByPieceId(pieceId)
    }

    override fun saveAllAssignment(pieceAssignments: List<PieceAssignment>): List<PieceAssignment> {
        return assignmentJpaRepository.saveAll(pieceAssignments)
    }

}