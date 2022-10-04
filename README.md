Autoversion is a Gradle plugin which generates a version name and code based on the most recent git tag. 
It's very similar to the `git describe` command:
 - if the current commit is not a tag, the version name is `<recent-tag>.<commit-count-since-tag>-<current-commit-hash>`
 - if the current commit is tagged, the version name is the name of the tag
The `code` is the number of commits on the current branch. It is intended to be used as an ever-increasing version code.
The `releaseCode` is the number of commits until the most recent tag. It is intended to be used as a development version code that is set to the value used by the production code.

#Usage

Groovy
```groovy
autoversion.name
autoversion.code
autoversion.releaseCode
```

Kotlin
```kotlin
project.autoversion().name
project.autoversion().code
project.autoversion().releaseCode
```
