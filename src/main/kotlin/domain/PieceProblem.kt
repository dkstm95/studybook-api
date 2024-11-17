package com.seungilahn.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class PieceProblem(
    val pieceId: Long,

    val problemId: Long,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {
    companion object {
        fun withoutId(pieceId: Long, problemId: Long) = PieceProblem(pieceId, problemId)
    }
}