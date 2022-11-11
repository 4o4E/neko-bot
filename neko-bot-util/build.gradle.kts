plugins {
    kotlin("jvm")
}

dependencies {
    // mirai
    compileOnly(mirai("core")) {
        exclude("org.apache.logging.log4j")
    }
    // test
    testImplementation(kotlin("test", Versions.kotlin))
    testImplementation(mirai("core"))
}