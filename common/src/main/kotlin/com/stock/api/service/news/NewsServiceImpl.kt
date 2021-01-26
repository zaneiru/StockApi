package com.stock.api.service.news

import com.stock.api.entity.news.News
import com.stock.api.repository.news.NewsRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class NewsServiceImpl(private val newsRepository: NewsRepository): NewsService {

    @Transactional(readOnly = true)
    override fun getNews(pageable: Pageable): Flux<News> =
        Mono.fromCallable { newsRepository.findByOrderByScrapeDate(pageable) }
            .subscribeOn(Schedulers.parallel())
            .flatMapMany { Flux.fromIterable(it) }
}