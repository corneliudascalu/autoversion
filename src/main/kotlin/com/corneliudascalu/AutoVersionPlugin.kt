package com.corneliudascalu

import org.gradle.api.Plugin
import org.gradle.api.Project

private const val EXTENSION_NAME = "versionName"

class AutoVersionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.create(
                EXTENSION_NAME,
                VersionExtension::class.java)
        Thread.sleep(1000)

        val versionExtension = target.extensions.getByType(VersionExtension::class.java) as VersionExtension
        versionExtension.name = "test"
    }
}

open class VersionExtension {
    var name:String = ""
}