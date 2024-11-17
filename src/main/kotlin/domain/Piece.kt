package com.seungilahn.domain

import jakarta.persistence.*

@Entity
class Piece(
    teacherId: Long,

    name: String,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ?= null
) {
    @Column(nullable = false)
    var teacherId: Long = teacherId
        protected set

    @Column(nullable = false)
    var name: String = name
        protected set

    companion object {
        fun withoutId(teacherId: Long, name: String) = Piece(teacherId, name)
    }
}