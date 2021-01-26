package com.stock.api.service.news

import com.stock.api.entity.news.News
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

interface NewsService {
    fun getNews(pageable: Pageable): Flux<News>
}