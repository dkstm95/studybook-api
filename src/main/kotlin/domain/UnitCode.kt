package com.seungilahn.domain

import jakarta.persistence.*

@Entity
class UnitCode(
    @Column(nullable = false)
    val code: String,

    @Column(nullable = false)
    val name: String,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : BaseTimeEntity() {
    companion object {
        fun create(code: String, name: String) = UnitCode(code, name)
    }
}