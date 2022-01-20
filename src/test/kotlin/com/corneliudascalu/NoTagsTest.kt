package com.corneliudascalu

import com.lordcodes.turtle.shellRun
import org.assertj.core.api.Assertions
import org.gradle.testfixtures.ProjectBuilder
import org.junit.After
import org.junit.Before
import org.junit.Test

class NoTagsTest {
    private var thisBranch = ""

    @Before
    fun setUp() {
        thisBranch = shellRun("git", listOf("rev-parse", "--abbrev-ref", "HEAD"))
        shellRun("git", listOf("stash"))
        shellRun("git", listOf("checkout", "--orphan", "test-branch"))
        shellRun("git", listOf("commit", "--allow-empty", "-m \"dummy commit\""))
    }

    @After
    fun tearDown() {
        shellRun("git", listOf("checkout", thisBranch))
        shellRun("git", listOf("stash", "pop"))
        shellRun("git", listOf("br", "-D", "test-branch"))
    }

    @Test
    fun `when there is no tag found, we get a readable error`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply(AutoVersionPlugin::class.java)

        val versionName: VersionExtension = project.extensions.getByName("autoversion") as VersionExtension

        val version = shellRun("git", listOf("describe", "--tags", "--dirty"))
        val tokens = version.split("-")
        val commitHash = tokens[2].removePrefix("g")
        val cleanVersion = "${tokens[0]}.${tokens[1]}-$commitHash"
        val hyphenVersion = "${tokens[0]}-${tokens[1]}-$commitHash"

        Assertions.assertThat(versionName).isNotNull
        Assertions.assertThat(versionName.name).isEqualToIgnoringCase(cleanVersion)
        Assertions.assertThat(versionName.nameHyphenated).isEqualToIgnoringCase(hyphenVersion)
    }

}