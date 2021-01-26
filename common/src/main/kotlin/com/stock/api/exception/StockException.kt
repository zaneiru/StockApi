package com.stock.api.exception

import com.stock.api.exception.enums.ErrorMessage

class StockException : RuntimeException {
    var errorCode: Int
    override var message: String = ""

    constructor(errorMessage: ErrorMessage) : super(errorMessage.getMessage()) {
        errorCode = errorMessage.getErrorCode()
        message = errorMessage.getMessage()
    }

    constructor(errorMessage: String?, errorCode: Int) : super(errorMessage) {
        this.errorCode = errorCode
    }

    constructor(errorMessage: ErrorMessage, t: Throwable?) : super(errorMessage.getMessage(), t) {
        errorCode = errorMessage.getErrorCode()
    }

    constructor(msg: String?, t: Throwable?) : super(msg, t) {
        errorCode = 99999
    }

    constructor(msg: String?) : super(msg) {
        errorCode = 99999
    }

    constructor(t: Throwable?) : super(t) {
        errorCode = 99999
    }
}