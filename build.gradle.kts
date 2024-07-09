plugins {
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

application.mainClass = "com.tokra.Main"
group = "org.mif"
version = "1.0"

val jdaVersion = "5.0.0-beta.24"



repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:$jdaVersion")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("io.github.cdimascio:dotenv-java:2.2.0")
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("ch.qos.logback:logback-classic:1.4.12")
    implementation("org.xerial:sqlite-jdbc:3.46.0.0")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.isIncremental = true
    sourceCompatibility = "1.9"
}