package com.corneliudascalu

import com.lordcodes.turtle.shellRun
import org.assertj.core.api.Assertions.assertThat
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

class AutoVersionPluginTest {

    @Before
    fun setUp() {
    }

    @Test
    fun testPluginApplied() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("com.corneliudascalu.autoversion")

        assertThat(project.plugins.findPlugin(AutoVersionPlugin::class.java)).isNotNull
    }

    @Test
    fun testExtension() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("com.corneliudascalu.autoversion")

        val versionName: VersionExtension = project.extensions.getByName("versionName") as VersionExtension

        val version = shellRun("git", listOf("describe", "--tags"))

        assertThat(versionName).isNotNull
        assertThat(versionName.name).isEqualToIgnoringCase(version)
    }
}