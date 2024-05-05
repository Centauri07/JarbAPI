plugins {
    kotlin("jvm") version "1.9.23"
    java
    `maven-publish`
}

group = "me.centauri07.jarbapi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    
    maven(url = uri("https://jitpack.io"))
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.21")

    implementation("com.google.code.gson:gson:2.10.1")

    implementation("net.dv8tion:JDA:5.0.0-beta.23")
    implementation("com.github.andeng07:DiscordCommand:8728f3ec79")
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "me.centauri07.jarbapi"
            artifactId = "JarbAPI"
            version = "1.0-SNAPSHOT"

            from(components["java"])
        }
    }
}

