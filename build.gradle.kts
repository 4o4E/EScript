import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("org.jetbrains.kotlin.kapt") version "1.6.20"
}

allprojects {
    group = "top.e404"
    version = "1.0.1"
}

repositories {
    // spigot
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    // papi
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    // vault
    maven("https://jitpack.io")
    mavenCentral()
    mavenLocal()
}

dependencies {
    // reflect
    implementation(kotlin("reflect"))
    // spigot
    compileOnly("org.spigotmc:spigot-api:1.13.2-R0.1-SNAPSHOT")
    // papi
    compileOnly("me.clip:placeholderapi:2.10.10")
    // vault
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    // bstats
    implementation("org.bstats:bstats-bukkit:3.0.0")
    // apt
    implementation(project(":apt"))
    kapt(project(":apt"))
}

tasks {
    shadowJar {
        archiveFileName.set("${project.name}-${project.version}.jar")
        exclude("META-INF/*")
        relocate("org.bstats", "top.e404.escript.bstats")
        doFirst {
            for (file in File("jar").listFiles() ?: arrayOf()) {
                println("正在删除`${file.name}`")
                file.delete()
            }
        }

        doLast {
            File("jar").mkdirs()
            for (file in File("build/libs").listFiles() ?: arrayOf()) {
                println("正在复制`${file.name}`")
                file.copyTo(File("jar/${file.name}"), true)
            }
        }
    }

    processResources {
        filesMatching("plugin.yml") {
            expand(project.properties)
        }
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}