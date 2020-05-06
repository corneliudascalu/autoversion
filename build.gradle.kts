plugins {
    kotlin("jvm") version "1.3.72"
    id("java-gradle-plugin")
}

repositories {
    jcenter()
}

gradlePlugin {
    plugins {
        create("autoversion") {
            id = "com.corneliudascalu.autoversion"
            implementationClass = "com.corneliudascalu.AutoVersionPlugin"
        }
    }
}

dependencies {
    implementation(kotlin("stdlib", "1.3.72"))

    testImplementation("com.lordcodes.turtle:turtle:0.2.0")
    testImplementation("junit:junit:4.13")
    testImplementation("org.assertj:assertj-core:3.11.1")
}