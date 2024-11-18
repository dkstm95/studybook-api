package com.seungilahn.domain

import jakarta.persistence.*

@Entity
class Piece(
    teacherId: Long,

    name: String,

    activated: Boolean,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ?= null
) : BaseTimeEntity() {
    @Column(nullable = false)
    var teacherId = teacherId
        protected set

    @Column(nullable = false)
    var name = name
        protected set

    @Column(nullable = false)
    var activated = activated
        protected set

    fun assignToStudents(studentIds: List<Long>, teacherId: Long): List<PieceAssignment> {
        require(this.teacherId == teacherId) { "본인이 생성한 학습지만 배정할 수 있습니다." }
        return studentIds.map { PieceAssignment.create(studentId = it, piece = this) }
    }

    fun analyze(problems: List<Problem>, grades: List<StudentProblemGrade>): PieceAnalysis =
        PieceAnalysis(piece = this, problems = problems, grades = grades)

    companion object {
        private const val MAX_PIECE_PROBLEMS_COUNT = 50

        fun create(teacherId: Long, name: String, problems: List<Problem>): Piece {
            require(problems.isNotEmpty() && problems.size <= MAX_PIECE_PROBLEMS_COUNT) {
                "문제는 1개 이상 ${MAX_PIECE_PROBLEMS_COUNT}개 이하로 선택해주세요."
            }
            return Piece(teacherId = teacherId, name = name, activated = true)
        }
    }
}