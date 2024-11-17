package com.seungilahn.adapter.`in`.web

import com.seungilahn.application.port.`in`.CreatePieceRequest
import com.seungilahn.application.port.`in`.CreatePieceResponse
import com.seungilahn.application.port.`in`.PieceService
import com.seungilahn.common.ApiResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PieceController(
    private val pieceService: PieceService
) {

    @PostMapping("/piece")
    fun create(@RequestBody @Valid request: CreatePieceRequest): ApiResponse<CreatePieceResponse> = ApiResponse.success(
        pieceService.create(request.teacherId!!, request.pieceName!!, request.problemIds!!)
    )

}