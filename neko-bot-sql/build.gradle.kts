plugins {
    kotlin("jvm")
}

dependencies {
    // util
    implementation("top.e404:neko-bot-util:${Versions.version}")
    // mysql
    implementation("mysql:mysql-connector-java:8.0.30")
    // hikari
    implementation("com.zaxxer:HikariCP:5.0.1")
    // mybatis
    implementation("org.mybatis:mybatis:3.5.11")
    // ehcache
    implementation("org.mybatis.caches:mybatis-ehcache:1.2.2")
    // test
    testImplementation(kotlin("test", Versions.kotlin))
    // log4j2
    testImplementation(log4j("core"))
    testImplementation(log4j("slf4j-impl"))
    // 异步
    testImplementation("com.lmax:disruptor:3.4.4")
}