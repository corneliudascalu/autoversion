package com.corneliudascalu

import org.codehaus.groovy.runtime.ProcessGroovyMethods

class AutoVersion {

    val versionCode: Int
        get() = getCommitCount()

    val versionName: String
        get() = getBuildVersion()

    val versionNameHyphen: String
        get() = getHyphenatedBuildVersion()

    private fun getCommitCount(): Int {
        val cmd = "git rev-list --count HEAD"
        return cmd.execute().toInt()
    }

    private fun getCommitHash(): String {
        val cmd = "git rev-list --max-count=1 --abbrev-commit HEAD"
        return cmd.execute()
    }

    private fun getCommitCountSinceTag(): Int {
        val commitDescription = "git describe --tags --long".execute()
        val pieces = commitDescription.split("-")
        return pieces[pieces.size - 2].toInt()
    }

    private fun getBuildVersion(): String {
        val version = "git describe --tags --dirty --abbrev=0".execute()
        val commitCount: Int = getCommitCountSinceTag()
        if (commitCount > 0) {
            return "${version}.${commitCount}-${getCommitHash()}"
        } else {
            return version
        }
    }

    private fun getHyphenatedBuildVersion(): String {
        val version = "git describe --tags --dirty --abbrev=0".execute()
        val commitCount: Int = getCommitCountSinceTag()
        if (commitCount > 0) {
            return "${version}-${commitCount}-${getCommitHash()}"
        } else {
            return version
        }
    }

    private fun String.execute(): String {
        val execute = ProcessGroovyMethods.execute(this)
        return ProcessGroovyMethods.getText(execute).trim()
    }
}