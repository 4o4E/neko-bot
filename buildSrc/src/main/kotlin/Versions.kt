object Versions {
    const val group = "top.e404"
    const val version = "6.0.0"

    const val kotlin = "1.7.20"
    const val kapt = "1.7.20"
    const val ktor = "1.6.7"
    const val skiko = "0.7.20"
    const val mirai = "2.13.0-RC"
    const val javafx = "11.0.2"
    const val log4j = "2.17.2"
    const val jline = "3.21.0"
}

fun kotlinx(id: String, version: String) = "org.jetbrains.kotlinx:kotlinx-$id:$version"
fun skiko(module: String, version: String = Versions.skiko) = "org.jetbrains.skiko:skiko-awt-runtime-$module:$version"
fun mirai(module: String, version: String = Versions.mirai) = "net.mamoe:mirai-$module:$version"
fun javafx(module: String, os: String, version: String = Versions.javafx) = "org.openjfx:javafx-$module:$version:$os"
fun log4j(module: String, version: String = Versions.log4j) = "org.apache.logging.log4j:log4j-$module:$version"
fun jline(name: String, version: String = Versions.jline) = "org.jline:$name:$version"
fun ktor(module: String, version: String = Versions.ktor) = "io.ktor:ktor-$module:$version"