package org.archguard.archdoc

import io.kotest.matchers.shouldBe
import org.jetbrains.kotlinx.jupyter.EvalRequestData
import org.jetbrains.kotlinx.jupyter.ReplForJupyter
import org.jetbrains.kotlinx.jupyter.ReplForJupyterImpl
import org.jetbrains.kotlinx.jupyter.api.Code
import org.jetbrains.kotlinx.jupyter.libraries.EmptyResolutionInfoProvider
import org.jetbrains.kotlinx.jupyter.messaging.DisplayHandler
import org.junit.jupiter.api.Test
import java.io.File

internal class DslTest {
    private fun makeEmbeddedRepl(): ReplForJupyter {
        val resolutionInfoProvider = EmptyResolutionInfoProvider

        val embeddedClasspath: List<File> = System.getProperty("java.class.path").split(File.pathSeparator).map(::File)
        return ReplForJupyterImpl(resolutionInfoProvider, embeddedClasspath, isEmbedded = true)
    }

    private val repl = makeEmbeddedRepl()

    fun eval(code: Code, displayHandler: DisplayHandler? = null, jupyterId: Int = -1, storeHistory: Boolean = true) =
        repl.eval(EvalRequestData(code, displayHandler, jupyterId, storeHistory))

    @Test
    internal fun simple_eval() {
        eval("val x = 3")
        val res = eval("x*2")
        res.resultValue shouldBe 6
    }

    @Test
    internal fun local_file() {
        eval(
            """
            @file:DependsOn("fixtures/doc-executor-1.7.0.jar")
            import org.archguard.dsl.*
            var layer = layered {
                prefixId("org.archguard")
                component("controller") dependentOn component("service")
                组件("service") 依赖于 组件("repository")
            }
            """
        )

        val res = eval("layer.components().size")
        res.resultValue shouldBe 3
    }

}