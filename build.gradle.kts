plugins {
    kotlin("jvm") version "1.5.31"
    java
}

group = "me.centauri07.jarbapi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.21")

    implementation("net.dv8tion:JDA:5.0.0-alpha.11")
    implementation(kotlin("reflect"))
}