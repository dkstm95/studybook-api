package com.seungilahn.adapter.out.persistence

import com.seungilahn.application.port.out.PieceProblemRepository
import com.seungilahn.domain.PieceProblem
import org.springframework.stereotype.Repository

@Repository
class PieceProblemRepositoryImpl(
    private val jpaRepository: SpringDataPieceProblemRepository
) : PieceProblemRepository {

    override fun findAllByPieceId(pieceId: Long): List<PieceProblem> {
        return jpaRepository.findAllByPieceId(pieceId)
    }

    override fun saveAll(pieceProblems: List<PieceProblem>): List<PieceProblem> {
        return jpaRepository.saveAll(pieceProblems)
    }

}