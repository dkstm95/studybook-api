package com.seungilahn.application.port.`in`

import com.seungilahn.adapter.`in`.web.GetProblemsResponse
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
    ): List<GetProblemsResponse> {

        val filteredProblems = problemRepository.findProblemsByCriteria(
            ProblemSearchCriteria(
                unitCodeList = unitCodeList,
                problemType = problemType,
                levelRange = levelCategory.getLevelRange()
            )
        )

        if (filteredProblems.isEmpty()) {
            return emptyList()
        }

        val easyProblems = filteredProblems.filter { it.level == 1 }
        val mediumProblems = filteredProblems.filter { it.level in 2..4 }
        val hardProblems = filteredProblems.filter { it.level == 5 }

        val easyCount = (totalCount * levelCategory.easyRate).toInt()
        val mediumCount = (totalCount * levelCategory.mediumRate).toInt()
        val hardCount = totalCount - easyCount - mediumCount

        val selectedProblems = easyProblems.take(easyCount) +
                mediumProblems.take(mediumCount) +
                hardProblems.take(hardCount)

        return selectedProblems.map { GetProblemsResponse.from(it) }
    }

}
