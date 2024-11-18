package com.seungilahn.domain

import jakarta.persistence.*

@Entity
class PieceAssignment(
    submitted: Boolean,

    @Column(nullable = false)
    val studentId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "piece_id")
    val piece: Piece,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ?= null
) : BaseTimeEntity() {
    @Column(nullable = false)
    var submitted = submitted
        protected set

    fun grade(problems: List<Problem>, studentAnswers: List<StudentAnswer>): StudentGradingResult {
        require(studentAnswers.isNotEmpty()) { "채점할 답안이 없습니다." }
        require(problems.map { it.id!! }.toSet().containsAll(studentAnswers.map { it.problemId })) {
            "학습지에 포함된 문제만 채점할 수 있습니다."
        }
        require(!this.submitted) { "이미 채점한 학생입니다." }
        this.submitted = true
        return StudentGradingResult.create(this, problems, studentAnswers)
    }

    companion object {
        fun create(studentId: Long, piece: Piece) =
            PieceAssignment(submitted = false, studentId = studentId, piece = piece)
    }
}