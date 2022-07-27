plugins {
    kotlin("jvm") version "1.5.31"
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.21")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.21")

    implementation("net.dv8tion:JDA:5.0.0-alpha.12")
    implementation("com.github.Centauri07:DCM:0.0.2")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
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

