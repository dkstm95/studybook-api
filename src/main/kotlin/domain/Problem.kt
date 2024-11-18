package com.seungilahn.domain

import jakarta.persistence.*

@Entity
class Problem(
    @Column(nullable = false)
    val difficultyLevel: Int,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: ProblemType,

    @Column(nullable = false)
    val answer: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_code_id")
    val unitCode: UnitCode,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : BaseTimeEntity() {
    fun isCorrect(studentAnswer: String): Boolean {
        return answer == studentAnswer
    }

    companion object {
        fun withId(id: Long, difficultyLevel: Int, type: ProblemType, answer: String, unitCode: UnitCode) =
            Problem(
                difficultyLevel = difficultyLevel,
                type = type,
                answer = answer,
                unitCode = unitCode,
                id = id
            )
    }
}