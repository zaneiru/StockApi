package com.stock.api.controller.item

import com.stock.api.controller.item.ItemController.Companion.BASE_URI
import com.stock.api.service.item.ItemService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [BASE_URI])
class ItemController(private val itemService: ItemService) {

//    @GetMapping
//    fun getItems(): Flux<ItemTO> {
//        val items = itemService.getItems()
//        return items.map(Item::toItemTO)
//    }

    companion object {
        const val BASE_URI: String = "/api/v1/items"
    }
}


