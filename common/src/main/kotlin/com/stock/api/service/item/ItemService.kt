package com.stock.api.service.item

import org.springframework.stereotype.Service

@Service
class ItemService() {
//      기존방
//    fun getItems(): List<Item> {
//        return itemRepository.findByAdminConfig(AdminConfig.ON)
//    }


//    fun getItems(): Flux<Item> = Mono
//        .fromCallable { itemRepository.findByAdminConfig(AdminConfig.ON) }
//        .subscribeOn(Schedulers.parallel())
//        .flatMapMany { Flux.fromIterable(it) }
}