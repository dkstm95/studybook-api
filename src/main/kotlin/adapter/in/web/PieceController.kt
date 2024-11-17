package com.seungilahn.adapter.`in`.web

import com.seungilahn.application.port.`in`.*
import com.seungilahn.common.ApiResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
class PieceController(
    private val pieceService: PieceService
) {

    // TODO: teacher authentication
    @PostMapping("/piece")
    fun create(@RequestBody @Valid request: CreatePieceRequest): ApiResponse<CreatePieceResponse> = ApiResponse.success(
        pieceService.create(request.teacherId!!, request.pieceName!!, request.problemIds!!)
    )

    // TODO: teacherId should be retrieved from authentication
    @PostMapping("/piece/{pieceId}")
    fun assign(
        @PathVariable pieceId: Long,
        @RequestParam studentIds: List<Long>
    ): ApiResponse<AssignPieceResponse> = ApiResponse.success(
        pieceService.assign(pieceId = pieceId, studentIds = studentIds, teacherId = 1)
    )

    // TODO: studentId should be retrieved from authentication
    @GetMapping("/piece/problems")
    fun getPieceProblems(
        @RequestParam pieceId: Long
    ): ApiResponse<GetPieceProblemResponse> = ApiResponse.success(
        pieceService.getPieceProblems(pieceId = pieceId, studentId = 1)
    )

}