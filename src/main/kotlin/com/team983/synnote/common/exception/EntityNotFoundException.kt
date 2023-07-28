package com.team983.synnote.common.exception

import com.team983.synnote.common.status.ResultCode

class EntityNotFoundException : BusinessException {
    constructor(resultCode: ResultCode) : super(resultCode)
}
