package com.seungilahn.application.port.`in`.response

data class AssignPieceResponse(
    val pieceId: Long,
    val assignedStudentsIds: List<Long>,
)
