package com.seungilahn.domain

enum class DifficultyCategory(
    private val easyWeight: Double,
    private val mediumWeight: Double,
    private val hardWeight: Double,
    val difficultyLevels: List<Int>
) {
    LOW(0.5, 0.3, 0.2, listOf(1))
    , MIDDLE(0.25, 0.5, 0.25, listOf(2, 3, 4))
    , HIGH(0.2, 0.3, 0.5, listOf(5))
    ;

    fun selectProblems(problems: List<Problem>, totalCount: Int): List<Problem> {
        val easyProblems = problems.filter { it.difficultyLevel in LOW.difficultyLevels }.shuffled()
        val mediumProblems = problems.filter { it.difficultyLevel in MIDDLE.difficultyLevels }.shuffled()
        val hardProblems = problems.filter { it.difficultyLevel in HIGH.difficultyLevels }.shuffled()

        val easyCount = (totalCount * easyWeight).toInt()
        val mediumCount = (totalCount * mediumWeight).toInt()
        val hardCount = totalCount - easyCount - mediumCount

        return easyProblems.take(easyCount) +
                mediumProblems.take(mediumCount) +
                hardProblems.take(hardCount)
    }

    companion object {
        fun fromString(category: String): DifficultyCategory {
            return valueOf(category.uppercase())
        }
    }
}