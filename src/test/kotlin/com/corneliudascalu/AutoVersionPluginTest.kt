package com.corneliudascalu

import com.lordcodes.turtle.shellRun
import org.assertj.core.api.Assertions.assertThat
import org.gradle.testfixtures.ProjectBuilder
import org.junit.After
import org.junit.Before
import org.junit.Test

class AutoVersionPluginTest {

    private var thisCommit = ""
    private var thisBranch = ""

    @Before
    fun setUp() {
        thisCommit = shellRun("git", listOf("rev-parse", "HEAD"))
        thisBranch = shellRun("git", listOf("rev-parse", "--abbrev-ref", "HEAD"))
        shellRun("git", listOf("stash"))
        shellRun("git", listOf("checkout", "-b", "test-branch"))
        shellRun("git", listOf("commit", "--allow-empty", "-m \"dummy commit\""))
    }

    @After
    fun tearDown() {
        shellRun("git", listOf("checkout", thisBranch))
        shellRun("git", listOf("reset", "--hard", thisCommit))
        shellRun("git", listOf("stash", "pop"))
        shellRun("git", listOf("br", "-D", "test-branch"))
    }

    @Test
    fun testPluginApplied() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("com.corneliudascalu.autoversion")

        assertThat(project.plugins.findPlugin(AutoVersionPlugin::class.java)).isNotNull
    }

    @Test
    fun `when there is at least one commit after the most recent tag, it is included in the version name`() {
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