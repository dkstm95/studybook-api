package com.seungilahn.domain

import jakarta.persistence.*

@Entity
class PieceProblem(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "piece_id")
    val piece: Piece,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    val problem: Problem,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {
    companion object {
        fun withoutId(piece: Piece, problem: Problem) = PieceProblem(piece, problem)
    }
}