package com.seungilahn.application.port.out

import com.seungilahn.domain.PieceProblem

interface PieceProblemRepository {
    fun findAllByPieceId(pieceId: Long): List<PieceProblem>
    fun saveAll(pieceProblems: List<PieceProblem>): List<PieceProblem>
}
