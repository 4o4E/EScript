plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.google.devtools.ksp:symbol-processing-api:2.0.0-1.0.21")
}

repositories {
    mavenCentral()
    mavenLocal()
}