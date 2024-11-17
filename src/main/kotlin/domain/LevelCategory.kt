package com.seungilahn.domain

data class LevelCategory(
    val name: String,
    val easyRate: Double,
    val mediumRate: Double,
    val hardRate: Double
) {

    companion object {
        private val LOW = LevelCategory("LOW", 0.5, 0.3, 0.2)
        private val MIDDLE = LevelCategory("MIDDLE", 0.25, 0.5, 0.25)
        private val HIGH = LevelCategory("HIGH", 0.2, 0.3, 0.5)

        fun fromString(value: String): LevelCategory {
            return when (value.uppercase()) {
                "LOW" -> LOW
                "MIDDLE" -> MIDDLE
                "HIGH" -> HIGH
                else -> throw IllegalArgumentException("Invalid level category: $value")
            }
        }
    }

    fun getLevelRange(): List<Int> {
        return when (name) {
            "LOW" -> listOf(1)
            "MIDDLE" -> listOf(2, 3, 4)
            "HIGH" -> listOf(5)
            else -> throw IllegalArgumentException("Invalid level category: $name")
        }
    }

}