package com.seungilahn.adapter.out.persistence

import com.seungilahn.application.port.out.PieceAssignmentRepository
import com.seungilahn.domain.PieceAssignment
import org.springframework.stereotype.Repository

@Repository
class PieceAssignmentRepositoryImpl(
    private val jpaRepository: SpringDataPieceAssignmentRepository,
) : PieceAssignmentRepository {

    override fun findByPieceIdAndStudentId(pieceId: Long, studentId: Long): PieceAssignment? {
        return jpaRepository.findByPieceIdAndStudentId(pieceId, studentId)
    }

    override fun findAllByPieceId(pieceId: Long): List<PieceAssignment> {
        return jpaRepository.findAllByPieceId(pieceId)
    }

    override fun saveAll(pieceAssignments: List<PieceAssignment>): List<PieceAssignment> {
        return jpaRepository.saveAll(pieceAssignments)
    }

}