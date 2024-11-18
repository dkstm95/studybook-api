package com.seungilahn.domain

import jakarta.persistence.*

@Entity
class Piece(
    teacherId: Long,

    name: String,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ?= null
) : BaseTimeEntity() {
    @Column(nullable = false)
    var teacherId: Long = teacherId
        protected set

    @Column(nullable = false)
    var name: String = name
        protected set

    fun isSameTeacher(teacherId: Long) = this.teacherId == teacherId

    fun analyze(problems: List<Problem>, grades: List<StudentProblemGrade>): PieceAnalysis =
        PieceAnalysis(piece = this, problems = problems, grades = grades)

    companion object {
        private const val MAX_PIECE_PROBLEMS_COUNT = 50

        fun create(teacherId: Long, name: String, problems: List<Problem>): Piece {
            require(problems.isNotEmpty() && problems.size <= MAX_PIECE_PROBLEMS_COUNT) {
                "문제는 1개 이상 ${MAX_PIECE_PROBLEMS_COUNT}개 이하로 선택해주세요."
            }
            return Piece(teacherId = teacherId, name = name)
        }

        fun withoutId(teacherId: Long, name: String) = Piece(teacherId, name)
    }
}