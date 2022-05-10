# ArchDoc

## Tasks

Kernel with [https://github.com/Kotlin/kotlin-jupyter](https://github.com/Kotlin/kotlin-jupyter)

UI with [https://github.com/jupyterlab/jupyterlab](https://github.com/jupyterlab/jupyterlab)

- [x] REPL tests
- [x] basic DSL design
- [ ] jupyter like markdown support
- [ ] Archdoc Editor
  - CodeMirror or Monaco Editor
  - Parser: `marked`
  - Math: `mathjax2`
- [ ] Archdoc DSL
- [ ] Archdoc Server

## DSL

```kotlin
layered {
    prefixId("org.archguard")

    component("controller") dependentOn component("service")
    组件("service") 依赖于 组件("repository")
}
```

## Setup

1. setup jupyter with Kotlin: [https://github.com/Kotlin/kotlin-jupyter](https://github.com/Kotlin/kotlin-jupyter)
2. save with `.ipynb` for local file
3. try editor api ?
4. analysis editor api?
5. design poc editor



