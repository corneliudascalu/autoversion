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

        val versionName: VersionExtension = project.extensions.getByName("autoversion") as VersionExtension

        val version = shellRun("git", listOf("describe", "--tags", "--dirty"))
        val tokens = version.split("-")
        val commitHash = tokens[2].removePrefix("g")
        val cleanVersion = "${tokens[0]}.${tokens[1]}-$commitHash"
        val hyphenVersion = "${tokens[0]}-${tokens[1]}-$commitHash"

        assertThat(versionName).isNotNull
        assertThat(versionName.name).isEqualToIgnoringCase(cleanVersion)
        assertThat(versionName.nameHyphenated).isEqualToIgnoringCase(hyphenVersion)
    }

    @Test
    fun testHelperFunction() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("com.corneliudascalu.autoversion")

        val autoversion = project.autoversion()

        assertThat(autoversion.name).isNotBlank()
    }
}