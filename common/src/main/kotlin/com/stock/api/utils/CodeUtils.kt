package com.stock.api.utils

fun multiIf(vararg pairs: Pair<Boolean, () -> Unit>) {
    pairs.forEach { (bool, func) -> if (bool) func() }
}