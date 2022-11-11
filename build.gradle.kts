import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Versions.kotlin
    kotlin("plugin.serialization") version Versions.kotlin
    `maven-publish`
    `java-library`
}

allprojects {
    group = Versions.group
    version = Versions.version

    repositories {
        mavenLocal()
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.gradle.maven-publish")
    apply(plugin = "org.gradle.java-library")

    dependencies {
        // slf4j
        implementation("org.slf4j:slf4j-api:1.7.36")
    }

    java {
        withJavadocJar()
        withSourcesJar()
    }

    afterEvaluate {
        publishing.publications.create<MavenPublication>("java") {
            from(components["kotlin"])
            artifact(tasks.getByName("sourcesJar"))
            artifact(tasks.getByName("javadocJar"))
            artifactId = project.name
            groupId = Versions.group
            version = Versions.version
        }
    }

    tasks {
        test {
            useJUnitPlatform()
            workingDir = project.projectDir.resolve("run")
            doFirst {
                if (workingDir.isFile) workingDir.delete()
                workingDir.mkdirs()
            }
        }

        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "11"
        }
    }
}