package com.seungilahn.application.port.`in`

import com.seungilahn.application.port.`in`.response.GetProblemResponse
import com.seungilahn.application.port.out.ProblemRepository
import com.seungilahn.domain.LevelCategory
import com.seungilahn.domain.ProblemSearchCriteria
import com.seungilahn.domain.ProblemType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ProblemQueryService(
    private val problemRepository: ProblemRepository,
) {

    fun getProblems(
        totalCount: Int,
        unitCodeList: List<String>,
        levelCategory: LevelCategory,
        problemType: ProblemType
    ): List<GetProblemResponse> {

        val filteredProblems = problemRepository.fetchProblemsByCriteria(
            ProblemSearchCriteria(
                unitCodeList = unitCodeList,
                problemType = problemType,
                levelRange = levelCategory.problemLevels
            )
        )

        if (filteredProblems.isEmpty()) {
            return emptyList()
        }

        val selectedProblems = levelCategory.selectProblems(filteredProblems, totalCount)

        return selectedProblems.map { GetProblemResponse.from(it) }
    }

}
