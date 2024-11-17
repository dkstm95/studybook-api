package com.seungilahn.application.port.out

import com.seungilahn.domain.PieceProblem

interface PieceProblemRepository {
    fun saveAll(pieceProblems: List<PieceProblem>)
}
