package com.team983.synnote.common.status

enum class ResultCode(val code: Int, val msg: String) {
    SUCCESS(0, "OK"),
    ERROR(99, "ERROR"),

    INVALID_INPUT_VALUE(1, "적절하지 않은 요청 값입니다.")
}
