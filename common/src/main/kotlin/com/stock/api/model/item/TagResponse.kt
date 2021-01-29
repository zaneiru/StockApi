package com.stock.api.model.item

import com.stock.api.entity.common.Tag

class TagResponse (
    val id: Long,
    val name: String
)

fun Tag.toTagResponse(): TagResponse {
    return TagResponse(
        id = this.id!!,
        name = this.name
    )
}