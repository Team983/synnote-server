package com.team983.synnote.common.dto

import com.team983.synnote.common.status.ResultCode

data class BaseResponse<T>(
    val resultCode: String = ResultCode.SUCCESS.code,
    val message: String = ResultCode.SUCCESS.msg,
    val data: T? = null
)
