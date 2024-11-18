package com.seungilahn.domain

data class StudentAnswer(
    val problemId: Long,
    val answer: String
) {
    init {
        require(answer.isNotBlank()) { "Answer must not be blank" }
    }
}
