package com.seungilahn.domain

enum class LevelCategory(
    private val easyRate: Double,
    private val mediumRate: Double,
    private val hardRate: Double,
    val problemLevels: List<Int>
) {
    LOW(0.5, 0.3, 0.2, listOf(1))
    , MIDDLE(0.25, 0.5, 0.25, listOf(2, 3, 4))
    , HIGH(0.2, 0.3, 0.5, listOf(5))
    ;

    fun selectProblems(problems: List<Problem>, totalCount: Int): List<Problem> {
        val easyProblems = problems.filter { it.level in LOW.problemLevels }.shuffled()
        val mediumProblems = problems.filter { it.level in MIDDLE.problemLevels }.shuffled()
        val hardProblems = problems.filter { it.level in HIGH.problemLevels }.shuffled()

        val easyCount = (totalCount * easyRate).toInt()
        val mediumCount = (totalCount * mediumRate).toInt()
        val hardCount = totalCount - easyCount - mediumCount

        return easyProblems.take(easyCount) +
                mediumProblems.take(mediumCount) +
                hardProblems.take(hardCount)
    }

    companion object {
        fun fromString(level: String): LevelCategory {
            return when (level.uppercase()) {
                "LOW" -> LOW
                "MIDDLE" -> MIDDLE
                "HIGH" -> HIGH
                else -> throw IllegalArgumentException("Invalid level category: $level")
            }
        }
    }

}