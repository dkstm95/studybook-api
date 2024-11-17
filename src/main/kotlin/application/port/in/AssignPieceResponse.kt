package com.seungilahn.application.port.`in`

data class AssignPieceResponse(
    val pieceId: Long,
    val assignedStudentsIds: List<Long>,
)
