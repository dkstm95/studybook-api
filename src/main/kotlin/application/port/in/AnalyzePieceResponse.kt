package com.seungilahn.application.port.`in`

data class AnalyzePieceResponse(
    val pieceId: Long,
    val pieceName: String,
    val students: List<StudentData>,
    val problemStatistics: List<ProblemStatistics>
) {
    data class StudentData(
        val studentId: Long,
        val correctRate: Double,
    )

    data class ProblemStatistics(
        val problemId: Long,
        val correctRate: Double,
    )
}
