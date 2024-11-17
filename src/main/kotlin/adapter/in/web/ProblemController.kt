package com.seungilahn.adapter.`in`.web

import com.seungilahn.application.port.`in`.ProblemQueryService
import com.seungilahn.common.ApiResponse
import com.seungilahn.domain.LevelCategory
import com.seungilahn.domain.ProblemType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ProblemController(
    private val problemQueryService: ProblemQueryService
) {

    @GetMapping("/problems")
    fun getProblems(
        @RequestParam totalCount: Int,
        @RequestParam unitCodeList: List<String>,
        @RequestParam level: String,
        @RequestParam problemType: String
    ): ApiResponse<List<GetProblemResponse>> = ApiResponse.success(
        problemQueryService.getProblems(
            totalCount,
            unitCodeList,
            LevelCategory.fromString(level),
            ProblemType.fromString(problemType)
        )
    )

}