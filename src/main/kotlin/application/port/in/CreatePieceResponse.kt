package com.seungilahn.application.port.`in`

import com.seungilahn.adapter.`in`.web.GetProblemResponse

data class CreatePieceResponse(
    val pieceId: Long,
    val pieceName: String,
    val teacherId: Long,
    val problems: List<GetProblemResponse>
)