package com.seungilahn.application.port.`in`.response

data class GetPieceProblemResponse(
    val pieceId: Long,
    val problems: List<GetProblemResponse>
)
