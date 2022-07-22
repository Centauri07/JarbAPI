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
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.21")

    implementation("net.dv8tion:JDA:5.0.0-alpha.12")
    implementation(files("C:\\Users\\Andrei\\Documents\\Programming\\Libraries and APIs\\DCM\\build\\libs\\DiscordCommandManager-v1.0-alpha.jar"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}