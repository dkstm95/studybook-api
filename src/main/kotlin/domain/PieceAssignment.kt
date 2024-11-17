package com.seungilahn.domain

import jakarta.persistence.*

@Entity
class PieceAssignment(
    @Column(nullable = false)
    val studentId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "piece_id")
    val piece: Piece,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ?= null
) : BaseTimeEntity() {
    companion object {
        fun withoutId(studentId: Long, piece: Piece) = PieceAssignment(studentId, piece)
    }
}