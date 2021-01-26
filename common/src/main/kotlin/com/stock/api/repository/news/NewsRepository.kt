package com.stock.api.repository.news

import com.stock.api.entity.news.News
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NewsRepository: JpaRepository<News, Long> {
    fun findByOrderByScrapeDate(pageable: Pageable): Page<News>
}