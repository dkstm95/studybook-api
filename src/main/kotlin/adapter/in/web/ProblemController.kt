package com.seungilahn.adapter.`in`.web

import com.seungilahn.application.port.`in`.ProblemQueryService
import com.seungilahn.application.port.`in`.response.GetProblemResponse
import com.seungilahn.common.ApiResponse
import com.seungilahn.domain.DifficultyCategory
import com.seungilahn.domain.ProblemType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ProblemController(
    private val problemQueryService: ProblemQueryService
) {

    // TODO: teacher authentication
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
            DifficultyCategory.fromString(level),
            ProblemType.fromString(problemType)
        )
    )

}