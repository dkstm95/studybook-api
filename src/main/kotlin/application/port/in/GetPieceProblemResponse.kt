package com.seungilahn.application.port.`in`

import com.seungilahn.adapter.`in`.web.GetProblemResponse

data class GetPieceProblemResponse(
    val pieceId: Long,
    val problems: List<GetProblemResponse>
)
