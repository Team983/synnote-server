package com.team983.synnote.common.status

enum class ResultCode(val code: String, val msg: String) {
    // Common
    SUCCESS("C_00", "OK"),
    ERROR("C_99", "ERROR"),
    INVALID_INPUT_VALUE("C_01", "적절하지 않은 요청 값입니다."),

    // User
    USER_NOT_FOUND("U_01", "해당 유저 엔티티를 찾을 수 없습니다."),

    // Note
    UPLOADTYPE_INIT_STATUS_NOT_MATCH("N_01", "업로드 타입과 상태가 매치되지 않습니다."),
    NOTE_NOT_FOUND("N_02", "해당 노트 엔티티를 찾을 수 없습니다."),
    NOTE_NOT_ACCESSED("N_03", "해당 노트를 접근할 수 있는 권한이 없습니다."),
    NOTE_ALREADY_HAS_RECORDING("N_04", "이미 녹음본이 존재하는 노트입니다."),
    NOTE_TITLE_LENGTH_EXCEEDED("N_05", "노트 제목의 길이가 50보다 초과되었습니다."),
    SCRIPT_NOTE_FOUND("N_06", "해당 스크립트 엔티티를 찾을 수 없습니다."),
    MEMO_NOTE_FOUND("N_07", "해당 메모 엔티티를 찾을 수 없습니다.")
}
