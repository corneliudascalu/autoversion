plugins {
    kotlin("jvm") version "1.3.72"
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "0.11.0"
}

repositories {
    jcenter()
}

group = "com.corneliudascalu"
version = "0.2"

gradlePlugin {
    plugins {
        create("autoversion") {
            id = "com.corneliudascalu.autoversion"
            displayName = "AutoVersion"
            description = "Auto versioning based on the most recent git tag"
            implementationClass = "com.corneliudascalu.AutoVersionPlugin"
        }
    }
}

pluginBundle {
    website = "http://www.corneliudascalu.com"
    vcsUrl = "https://github.com/corneliudascalu/autoversion"
    tags = listOf("versioning", "git", "tag")
}

dependencies {
    implementation(kotlin("stdlib", "1.3.72"))

    testImplementation("com.lordcodes.turtle:turtle:0.2.0")
    testImplementation("junit:junit:4.13")
    testImplementation("org.assertj:assertj-core:3.11.1")
}