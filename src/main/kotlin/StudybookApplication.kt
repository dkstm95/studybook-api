package com.seungilahn

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StudybookApplication

fun main(args: Array<String>) {
    runApplication<StudybookApplication>(*args)
}