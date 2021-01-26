package com.stock.api.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
@EnableRedisRepositories
@EnableCaching
class RedisConfig: CachingConfigurerSupport() {

    @Value("\${redis.news.host}")
    private val redisHost: String = ""

    @Value("\${redis.news.port}")
    private val redisPort: Int = 0

    @Value("\${redis.news.password}")
    private val redisPassword: String = ""

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration()
        redisStandaloneConfiguration.hostName = redisHost
        redisStandaloneConfiguration.port = redisPort
        redisStandaloneConfiguration.setPassword(redisPassword)

        val clientConfiguration: LettuceClientConfiguration = LettuceClientConfiguration.builder()
            .commandTimeout(Duration.ofSeconds(10000))
            .build()
        return LettuceConnectionFactory(redisStandaloneConfiguration, clientConfiguration)
    }

    @Bean("redisTemplate")
    fun redisTemplateWithLettuce(): RedisTemplate<String, Any> {

        val template = RedisTemplate<String, Any>()
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = GenericJackson2JsonRedisSerializer()
        template.hashKeySerializer = StringRedisSerializer()
        template.hashValueSerializer = GenericJackson2JsonRedisSerializer()

        template.setConnectionFactory(redisConnectionFactory())
        template.setEnableTransactionSupport(true)


        return template
    }
}