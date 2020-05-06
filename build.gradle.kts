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
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.1")
}