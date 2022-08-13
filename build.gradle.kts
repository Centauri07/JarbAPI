plugins {
    kotlin("jvm") version "1.6.10-RC"
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

    implementation("com.google.code.gson:gson:2.9.1")

    implementation("net.dv8tion:JDA:5.0.0-alpha.12")
    implementation("com.github.Centauri07:DCM:0.0.3")

    implementation("org.litote.kmongo:kmongo:4.6.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
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

