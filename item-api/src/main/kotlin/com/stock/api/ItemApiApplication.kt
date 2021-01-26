package com.stock.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

// 아래와 같이 코틀린에서는 SpringBootApplication 부분에서 main 함수를 class 밖에서 선언해준다.
@SpringBootApplication
class ItemApiApplication

// 기본적으로 함수 선언부 fun은 public이다.
fun main(args: Array<String>) {
    runApplication<ItemApiApplication>(*args)
}