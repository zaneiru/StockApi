package com.stock.api.controller

import com.stock.api.controller.NewsController.Companion.BASE_URI
import com.stock.api.controller.to.NewsResponseTO
import com.stock.api.controller.to.toNewsResponseTO
import com.stock.api.entity.news.News
import com.stock.api.service.news.NewsService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@Api(description = "뉴스 API")
@RestController
@RequestMapping(value = [BASE_URI])
class NewsController(val newsService: NewsService) {

    @ApiOperation("뉴스 목록")
    @GetMapping
    fun getNews(pageable: Pageable): Flux<NewsResponseTO> {
        val news: Flux<News> = newsService.getNews(pageable)
        return news.map(News::toNewsResponseTO)
    }

    companion object {
        const val BASE_URI: String = "/api/v1/news"
    }
}