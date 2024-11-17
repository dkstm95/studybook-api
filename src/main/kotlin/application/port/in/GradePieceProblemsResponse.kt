package com.seungilahn.application.port.`in`

data class GradePieceProblemsResponse(
    val pieceId: Long,
    val studentId: Long,
    val gradedProblems: List<GradedProblem>
) {
    data class GradedProblem(
        val problemId: Long,
        val studentAnswer: String,
        val correctAnswer: String,
        val isCorrect: Boolean
    )
}
