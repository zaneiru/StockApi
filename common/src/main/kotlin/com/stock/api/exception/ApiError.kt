package com.stock.api.exception

import com.fasterxml.jackson.annotation.JsonGetter
import org.springframework.http.HttpStatus

class ApiError(var errorCode: Int, var message: String, var status: HttpStatus) {

//    @get:JsonGetter("errorCode")
//    val errorCodeInt: Int
//        get() = errorCode
//
//    @get:JsonGetter("message")
//    val messageString: String
//        get() = message
//
//    @get:JsonGetter("status")
//    val statusString: String
//        get() = status.value().toString()

}