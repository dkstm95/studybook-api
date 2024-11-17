package com.seungilahn.domain

import jakarta.persistence.*

@Entity
class StudentProblemGrade(
    @Column(nullable = false)
    val studentId: Long,

    @Column(nullable = false)
    val pieceId: Long,

    @Column(nullable = false)
    val problemId: Long,

    @Column(nullable = false)
    val isCorrect: Boolean,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ?= null
) : BaseTimeEntity() {
    companion object {
        fun withoutId(studentId: Long, pieceId: Long, problemId: Long, isCorrect: Boolean) = StudentProblemGrade(
            studentId = studentId,
            pieceId = pieceId,
            problemId = problemId,
            isCorrect = isCorrect
        )
    }
}