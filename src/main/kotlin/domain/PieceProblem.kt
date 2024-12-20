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
) : BaseTimeEntity() {
    companion object {
        fun create(pieceId: Long, problemId: Long) = PieceProblem(pieceId, problemId)
    }
}