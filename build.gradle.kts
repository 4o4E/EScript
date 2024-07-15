plugins {
    kotlin("jvm") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("com.google.devtools.ksp") version "2.0.0-1.0.21"
}

allprojects {
    group = "top.e404"
    version = "1.0.10"
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
    // spigot
    compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
    // papi
    compileOnly("me.clip:placeholderapi:2.10.10")
    // vault
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    // bstats
    implementation("org.bstats:bstats-bukkit:3.0.2")
    // ksp
    implementation(project(":ksp"))
    ksp(project(":ksp"))
}

tasks {
    shadowJar {
        archiveFileName.set("${project.name}-${project.version}.jar")
        exclude("META-INF/**")
        relocate("org.bstats", "top.e404.escript.relocate.bstats")
        relocate("kotlin", "top.e404.escript.relocate.kotlin")
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
}