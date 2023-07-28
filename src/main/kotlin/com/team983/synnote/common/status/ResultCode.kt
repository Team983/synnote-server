package com.team983.synnote.common.status

enum class ResultCode(val code: String, val msg: String) {
    // Common
    SUCCESS("C_00", "OK"),
    ERROR("C_99", "ERROR"),
    INVALID_INPUT_VALUE("C_01", "적절하지 않은 요청 값입니다."),

    // User
    USER_NOT_FOUND("U_01", "해당 유저 엔티티를 찾을 수 없습니다.")
}
