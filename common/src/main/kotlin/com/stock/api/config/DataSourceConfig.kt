package com.stock.api.config


import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@Profile("local", "sandbox", "production")
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager",
    basePackages = ["com.stock.api.repository"]
)
class JpaConfig {

    @Value("\${spring.jpa.hibernate.naming.physical-strategy}")
    val physicalNamingStrategy: String = ""

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.stock")
    fun dataSource(): DataSource {
        val dataSource = DataSourceBuilder.create().type(HikariDataSource::class.java).build()
        dataSource.driverClassName = "com.mysql.cj.jdbc.Driver"
        dataSource.jdbcUrl = "jdbc:mysql://127.0.0.1:3306/stock"
        dataSource.username = "root"
        dataSource.password = "sprtms1225"

        return dataSource
    }

    @Primary
    @Bean
    fun entityManagerFactory(
        builder: EntityManagerFactoryBuilder,
        @Qualifier("dataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        val propertiesMaps = mapOf(
            Pair("hibernate.show_sql", "true"),
            Pair("hibernate.format_sql", "true"),
            Pair("hibernate.use_sql_comments", "true"),
            Pair("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect"),
            Pair("hibernate.physical_naming_strategy" , "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy")
        )

        return builder
            .dataSource(dataSource)
            .packages("com.stock.api.entity")
            .persistenceUnit("stock")
            .properties(propertiesMaps)
            .build()
    }

    @Primary
    @Bean
    fun transactionManager(@Qualifier("entityManagerFactory") entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {

        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory

        return transactionManager
    }
}