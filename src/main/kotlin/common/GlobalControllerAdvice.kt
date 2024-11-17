package com.seungilahn.common

import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class GlobalControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException, request: WebRequest): ApiResponse<Nothing> {
        val errors = ex.bindingResult.fieldErrors.map { it.defaultMessage }
        return ApiResponse.error(errors.joinToString(", "))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(ex: IllegalArgumentException, request: WebRequest): ApiResponse<Nothing> {
        return ApiResponse.error(ex.message ?: "Invalid input")
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGeneralException(ex: Exception, request: WebRequest): ApiResponse<Nothing> {
        return ApiResponse.error("An unexpected error occurred: ${ex.message}")
    }

}