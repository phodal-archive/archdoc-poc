# ArchDoc

with [https://github.com/Kotlin/kotlin-jupyter](https://github.com/Kotlin/kotlin-jupyter)

## DSL

```kotlin
layered {
    prefixId("org.archguard")

    component("controller") dependentOn component("service")
    组件("service") 依赖于 组件("repository")
}
```
