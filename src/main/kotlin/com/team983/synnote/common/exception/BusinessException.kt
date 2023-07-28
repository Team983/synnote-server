package com.team983.synnote.common.exception

import com.team983.synnote.common.status.ResultCode

open class BusinessException : RuntimeException {

    val resultCode: ResultCode

    constructor(resultCode: ResultCode) : super(resultCode.msg) {
        this.resultCode = resultCode
    }
}
