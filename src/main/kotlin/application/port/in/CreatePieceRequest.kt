package com.seungilahn.application.port.`in`

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreatePieceRequest(
    @field:NotNull(message = "Teacher ID must not be null")
    val teacherId: Long?,

    @field:NotBlank(message = "Piece name must not be blank")
    val pieceName: String?,

    @field:NotNull(message = "Level must not be null")
    val problemIds: List<Long>?
)
