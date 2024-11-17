package com.seungilahn.common

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class GlobalControllerAdvice {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException, request: WebRequest): ApiResponse<Nothing> {
        return ApiResponse.error(ex.message ?: "Invalid input")
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception, request: WebRequest): ApiResponse<Nothing> {
        return ApiResponse.error("An unexpected error occurred: ${ex.message}")
    }

}