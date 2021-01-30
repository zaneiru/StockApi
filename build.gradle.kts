import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "2.2.4.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.spring") version "1.4.10"
    kotlin("plugin.jpa") version "1.4.10"
    kotlin("kapt") version "1.4.10"
    kotlin("plugin.allopen") version "1.4.10"
    kotlin("plugin.noarg") version "1.4.10"
    idea
}

java.sourceCompatibility = JavaVersion.VERSION_14

allprojects {
    repositories {
        // 요게 없으면 Cannot resolve external dependency org.jetbrains.kotlin:kotlin-compiler-embeddable:1.3.21 because no repositories are defined. 발생
        jcenter() // mavenCentral 인건 상관없네.
        mavenCentral()
    }
}

noArg {
    annotation("com.stock.api.model.*.NoArg")
    annotation("com.stock.api.entity.NoArg")
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
    invokeInitializers = true
}

// Kotlin compile plugin
// 특정 어노테이션이 붙은 클래스와 그 클래스의 멤버를 자동으로 open 으로 만들어 준다.
allOpen {
    annotation("com.stock.api.entity.NoArg")
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

subprojects {

    apply(plugin = "idea")
    apply(plugin = "war")
    apply(plugin = "application")
    apply(plugin = "kotlin") // 요부분을 apply { plugin("kotlin")} -> apply(plugin="kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "kotlin-jpa")
    apply(plugin = "kotlin-spring")
    apply(plugin = "kotlin-noarg")

    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    group = "StockApi"
    version = "1.0.0"

    dependencies {
        compileOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
        compileOnly("org.jetbrains.kotlin:kotlin-reflect")
        compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        compileOnly("org.springframework.boot:spring-boot-starter-logging")


        compileOnly("org.springframework.boot:spring-boot-configuration-processor")
        testCompileOnly("org.springframework.boot:spring-boot-starter-test")

        // Koin for Kotlin
        implementation("org.koin:koin-core:2.0.1")
        implementation("org.koin:koin-core-ext:2.0.1")
        testImplementation("org.koin:koin-test:2.0.1")
        // Koin for Java developers
        implementation("org.koin:koin-java:2.0.1")
        implementation("org.koin:koin-ktor:2.0.1")

        implementation("org.hibernate.validator:hibernate-validator")
        implementation("org.apache.commons:commons-lang3")
        implementation("commons-validator:commons-validator:1.6")
        implementation("commons-io:commons-io:2.6")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

        implementation("mysql:mysql-connector-java")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")

        implementation("org.springframework.boot:spring-boot-starter-data-redis") {
            exclude(group = "io.lettuce", module = "lettuce-core")
        }
        implementation("io.lettuce:lettuce-core")
        implementation("redis.clients:jedis")

        implementation("au.com.console:kassava:2.1.0")

        // kotlin jpa specification
        implementation("au.com.console:kotlin-jpa-specification-dsl:2.0.0")
        implementation("com.querydsl:querydsl-jpa:4.2.1")
        kapt("com.querydsl:querydsl-apt:4.2.1:jpa")




        //implementation("org.jetbrains.kotlin.plugin.noarg:org.jetbrains.kotlin.plugin.noarg.gradle.plugin:1.3.61")
//
//        // Spring boot reactive Redis
//        implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive:2.4.1")
    }

    tasks {
        compileKotlin {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "1.8"
            }
            dependsOn(processResources) // kotlin 에서 ConfigurationProperties
        }


        compileTestKotlin {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "1.8"
            }
        }
    }
}

project("common") {

    apply(plugin = "kotlin-noarg")

    dependencies {
        implementation("org.springframework.data:spring-data-commons")
        runtimeOnly("com.h2database:h2")




    }

    val jar: Jar by tasks
    val bootJar: BootJar by tasks

    bootJar.enabled = false
    jar.enabled = true
}

sourceSets["main"].withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
    kotlin.srcDir("$buildDir/generated/source/kapt/main")
}

project(":member-api") {
    dependencies {
        compileOnly(project(":common"))

        implementation("org.springframework.data:spring-data-commons")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        testCompileOnly("org.springframework.boot:spring-boot-starter-test")

        implementation("io.springfox:springfox-swagger2:3.0.0")
        implementation("io.springfox:springfox-swagger-ui:3.0.0")
        implementation("org.springdoc:springdoc-openapi-webflux-ui:1.3.4")
        implementation("org.springdoc:springdoc-openapi-kotlin:1.3.4")
    }
}

project(":notice-api") {
    dependencies {
        compileOnly(project(":common"))

        implementation("org.springframework.data:spring-data-commons")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        testCompileOnly("org.springframework.boot:spring-boot-starter-test")

        implementation("io.springfox:springfox-swagger2:3.0.0")
        implementation("io.springfox:springfox-swagger-ui:3.0.0")
        implementation("org.springdoc:springdoc-openapi-webflux-ui:1.3.4")
        implementation("org.springdoc:springdoc-openapi-kotlin:1.3.4")
    }
}

project(":item-api") {
    dependencies {
        compileOnly(project(":common"))

        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.data:spring-data-commons")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        testCompileOnly("org.springframework.boot:spring-boot-starter-test")

        implementation("io.springfox:springfox-swagger2:3.0.0")
        implementation("io.springfox:springfox-swagger-ui:3.0.0")
        implementation("org.springdoc:springdoc-openapi-webflux-ui:1.3.4")
        implementation("org.springdoc:springdoc-openapi-kotlin:1.3.4")

    }
}

project(":news-api") {
    dependencies {
        compileOnly(project(":common"))

        implementation("org.springframework.data:spring-data-commons")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        testCompileOnly("org.springframework.boot:spring-boot-starter-test")

        implementation("io.springfox:springfox-swagger2:3.0.0")
        implementation("io.springfox:springfox-swagger-ui:3.0.0")
        implementation("org.springdoc:springdoc-openapi-webflux-ui:1.3.4")
        implementation("org.springdoc:springdoc-openapi-kotlin:1.3.4")
    }
}