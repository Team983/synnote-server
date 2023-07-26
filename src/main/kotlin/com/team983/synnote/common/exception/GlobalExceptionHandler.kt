package com.team983.synnote.common.exception

import com.team983.synnote.common.dto.BaseResponse
import com.team983.synnote.common.status.ResultCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun methodArgumentNotValidException(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<BaseResponse<Map<String, String>>> {
        val errors = mutableMapOf<String, String>()
        ex.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.defaultMessage
            errors[fieldName] = errorMessage ?: "Not Exception Message"
        }
        return ResponseEntity(
            BaseResponse(
                resultCode = ResultCode.INVALID_INPUT_VALUE.code,
                message = ResultCode.INVALID_INPUT_VALUE.msg,
                data = errors
            ), HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(Exception::class)
    protected fun defaultException(
        ex: Exception
    ): ResponseEntity<BaseResponse<Map<String, String>>> {
        val errors = mapOf("Unprocessed Error" to (ex.message ?: "Not Exception Message"))
        return ResponseEntity(
            BaseResponse(
                resultCode = ResultCode.ERROR.code,
                message = ResultCode.ERROR.msg,
                data = errors
            ), HttpStatus.BAD_REQUEST)
    }
}
