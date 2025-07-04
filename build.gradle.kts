plugins {
	java
	id("org.springframework.boot") version "3.4.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.vanphong"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
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
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("com.cloudinary:cloudinary-http44:1.39.0")
    implementation("com.google.firebase:firebase-admin:9.3.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.8")
	implementation("me.paulschwarz:spring-dotenv:4.0.0")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("com.google.cloud:google-cloud-translate:2.60.0")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.security:spring-security-crypto")
	implementation("org.apache.httpcomponents:fluent-hc:4.5.13")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
	implementation("net.logstash.logback:logstash-logback-encoder:7.4")
	implementation("com.google.auth:google-auth-library-oauth2-http:1.17.0")
	implementation("org.json:json:20240303")
	implementation("org.apache.httpcomponents.client5:httpclient5")
	implementation("com.fasterxml.jackson.core:jackson-databind")
	implementation("org.jsoup:jsoup:1.17.2")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
