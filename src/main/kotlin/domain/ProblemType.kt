package com.seungilahn.domain

enum class ProblemType(val typeName: String) {
    SELECTION("SELECTION")
    , SUBJECTIVE("SUBJECTIVE")
    , ALL("ALL")
    ;

    companion object {
        fun fromString(value: String): ProblemType {
            return entries.find { it.typeName.equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("Invalid problem type: $value")
        }
    }
}