package com.stock.api.utils

import java.util.*

object KeyUtils {

    fun getGenerateKey(): String {
        return UUID.randomUUID().toString()
    }
}