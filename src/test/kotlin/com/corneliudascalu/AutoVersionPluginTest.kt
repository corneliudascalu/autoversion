package com.corneliudascalu

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.assertj.core.data.Offset
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Assert.*
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
}