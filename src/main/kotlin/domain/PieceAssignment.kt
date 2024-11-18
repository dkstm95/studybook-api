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

    fun grade(problems: List<Problem>, studentAnswers: List<StudentAnswer>): StudentGradingResult {
        validateStudentAnswers(problems, studentAnswers)
        return StudentGradingResult.create(this, problems, studentAnswers)
    }

    private fun validateStudentAnswers(problems: List<Problem>, studentAnswers: List<StudentAnswer>) {
        require(studentAnswers.isNotEmpty()) { "채점할 답안이 없습니다." }
        require(problems.map { it.id!! }.toSet().containsAll(studentAnswers.map { it.problemId })) {
            "학습지에 포함된 문제만 채점할 수 있습니다."
        }
    }

    companion object {
        fun withoutId(studentId: Long, piece: Piece) = PieceAssignment(studentId, piece)
    }
}