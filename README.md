Autoversion is a Gradle plugin which generates a version name and code based on the most recent git tag. 
It's very similar to the `git describe` command:
 - if the current commit is not a tag, the version name is `<recent-tag>.<commit-count-since-tag>-<current-commit-hash>`
 - if the current commit is tagged, the version name is the name of the tag

#Usage

Groovy
```groovy
autoversion.name
autoversion.code
```

Kotlin
```kotlin
project.autoversion().name
project.autoversion().code
```
