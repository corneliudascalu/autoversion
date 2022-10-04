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
            versionExtension.vName = "v${autoVersion.versionName}"
            versionExtension.nameHyphenated = autoVersion.versionNameHyphen
            versionExtension.code = autoVersion.versionCode
            versionExtension.releaseCode = autoVersion.releaseVersionCode
        } catch (e: Exception) {
            print(e.message)
        }
    }
}

open class VersionExtension {
    /**
     * The version name generated on the most recent tag, using periods (.) to separate the version from commit hash.
     */
    var name: String = "0.0.1"

    /**
     * The version name generated on the most recent tag, using periods (.) to separate the version from commit hash, prefixed with a "v".
     */
    var vName: String = "v0.0.1"

    /**
     * The version name generated on the most recent tag, using hyphens (-) to separate the version from commit hash.
     */
    var nameHyphenated: String = "0.0.1"

    /**
     * The version code (the number of commits on the current branch since the beginning of time)
     */
    var code: Int = 1

    /**
     * The version code for the most recent release (the number of commits up to the release tag)
     */
    var releaseCode: Int = 1
}

fun Project.autoversion(): VersionExtension {
    return this.extensions.getByName(EXTENSION_NAME) as VersionExtension
}
