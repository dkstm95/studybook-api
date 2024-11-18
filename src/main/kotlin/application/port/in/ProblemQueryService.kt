package com.seungilahn.application.port.`in`

import com.seungilahn.application.port.`in`.response.GetProblemResponse
import com.seungilahn.application.port.out.ProblemRepository
import com.seungilahn.domain.DifficultyCategory
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
        difficultyCategory: DifficultyCategory,
        problemType: ProblemType
    ): List<GetProblemResponse> {

        val filteredProblems = problemRepository.fetchProblemsByCriteria(
            ProblemSearchCriteria(
                unitCodeList = unitCodeList,
                problemType = problemType,
                levelRange = difficultyCategory.difficultyLevels
            )
        )

        if (filteredProblems.isEmpty()) {
            return emptyList()
        }

        val selectedProblems = difficultyCategory.selectProblems(filteredProblems, totalCount)

        return selectedProblems.map { GetProblemResponse.from(it) }
    }

}
