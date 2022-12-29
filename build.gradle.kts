plugins {
    java
    id("com.github.johnrengelman.shadow").version("7.1.2")
}

group = "com.vertmix"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.lucko.me/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly("me.lucko:helper:5.6.13")
    compileOnly("me.lucko:helper-redis:1.2.1")

}


shadow {
    applicationDistribution.from("src/dist")
}

tasks.shadowJar {
    minimize()
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

