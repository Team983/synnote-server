import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.1"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.jlleitschuh.gradle.ktlint") version "11.5.0"
    id("com.epages.restdocs-api-spec") version "0.17.1"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.jpa") version "1.8.22"
}

group = "com.synnote"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // spring web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // spring jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // annotation processor
    annotationProcessor("org.projectlombok:lombok")

    // h2 DB
    runtimeOnly("com.h2database:h2")
    testImplementation("com.h2database:h2")

    // swagger
    testImplementation("com.epages:restdocs-api-spec-mockmvc:0.17.1")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")

    // kotest
    testImplementation("io.kotest:kotest-runner-junit5:5.5.5")
    testImplementation("io.kotest:kotest-assertions-core:5.5.5")
    testImplementation("io.kotest:kotest-extensions-spring:4.4.3")
    testImplementation("io.mockk:mockk:1.13.5")
}

tasks.register<Copy>("copyOasToSwagger") {
    delete("src/main/resources/static/swagger-ui/openapi3.yaml")
    from("$buildDir/api-spec/openapi3.yaml")
    into("src/main/resources/static/swagger-ui/.")
    dependsOn("openapi3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

openapi3 {
    this.setServer("https://dev.synnote.com:8080")
    title = "Synnote API Documentation"
    description = "Synnote API Documentation"
    version = "0.1.0"
    format = "yaml"
}
