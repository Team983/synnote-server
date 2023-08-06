package com.team983.synnote.common.exception.handler

import com.team983.synnote.common.dto.BaseResponse
import com.team983.synnote.common.exception.BusinessException
import com.team983.synnote.common.status.ResultCode
import lombok.extern.slf4j.Slf4j
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    protected fun businessException(
        ex: BusinessException
    ): ResponseEntity<BaseResponse<Map<String, String>>> {
        return ResponseEntity(
            BaseResponse(
                resultCode = ex.resultCode.code,
                message = ex.resultCode.msg
            ),
            HttpStatus.BAD_REQUEST
        )
    }

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
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(Exception::class)
    protected fun defaultException(
        ex: Exception
    ): ResponseEntity<BaseResponse<Map<String, String>>> {
        log.error(ex.message)
        val errors = mapOf("Unprocessed Error" to (ex.message ?: "Not Exception Message"))
        return ResponseEntity(
            BaseResponse(
                resultCode = ResultCode.ERROR.code,
                message = ResultCode.ERROR.msg,
                data = errors
            ),
            HttpStatus.BAD_REQUEST
        )
    }
}
