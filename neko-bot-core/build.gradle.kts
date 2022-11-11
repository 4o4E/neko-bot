plugins {
    id("java")
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    // mirai
    implementation(mirai("core")) {
        exclude("org.apache.logging.log4j")
    }
    implementation(mirai("core-utils"))
    // util
    implementation("top.e404:neko-bot-util:${Versions.version}")
    // sql
    implementation("top.e404:neko-bot-sql:${Versions.version}")
    // test
    testImplementation(kotlin("test", Versions.kotlin))
}