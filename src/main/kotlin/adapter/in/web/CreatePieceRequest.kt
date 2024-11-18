package com.seungilahn.adapter.`in`.web

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreatePieceRequest(
    @field:NotBlank(message = "Piece name must not be blank")
    val pieceName: String?,

    @field:NotNull(message = "Problem IDs must not be null")
    val problemIds: List<Long>?
)
