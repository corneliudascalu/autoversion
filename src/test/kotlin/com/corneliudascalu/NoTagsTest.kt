package com.corneliudascalu

import com.lordcodes.turtle.ShellRunException
import com.lordcodes.turtle.shellRun
import org.assertj.core.api.Assertions.assertThat
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
        try {
            shellRun("git", listOf("stash", "pop"))
        } catch (e: ShellRunException) {
            println("No stash entries")
        }
        shellRun("git", listOf("br", "-D", "test-branch"))
    }

    @Test
    fun `when there is no tag found, we don't crash`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply(AutoVersionPlugin::class.java)

        val versionName: VersionExtension = project.extensions.getByName("autoversion") as VersionExtension
        versionName.name
    }

    @Test
    fun `when there is no tag found, the version is the default one`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply(AutoVersionPlugin::class.java)

        val versionName: VersionExtension = project.extensions.getByName("autoversion") as VersionExtension

        assertThat(versionName.name).isEqualTo("0.0.1")
        assertThat(versionName.code).isEqualTo(1)
        assertThat(versionName.releaseCode).isEqualTo(1)
    }
}
