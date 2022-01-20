package com.corneliudascalu

import org.gradle.api.Plugin
import org.gradle.api.Project

private const val EXTENSION_NAME = "autoversion"

class AutoVersionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.create(
            EXTENSION_NAME,
            VersionExtension::class.java
        )

        val autoVersion = AutoVersion()

        val versionExtension = target.extensions.getByType(VersionExtension::class.java) as VersionExtension
        try {
            versionExtension.name = autoVersion.versionName
            versionExtension.nameHyphenated = autoVersion.versionNameHyphen
            versionExtension.code = autoVersion.versionCode
        } catch (e: Exception) {
            print(e.message)
        }
    }
}

open class VersionExtension {
    var name: String = ""
    var nameHyphenated: String = ""
    var code: Int = 0
}

fun Project.autoversion(): VersionExtension {
    return this.extensions.getByName(EXTENSION_NAME) as VersionExtension
}