package com.seungilahn.application.port.`in`.response

data class CreatePieceResponse(
    val pieceId: Long,
    val pieceName: String,
    val teacherId: Long,
    val problems: List<GetProblemResponse>
)