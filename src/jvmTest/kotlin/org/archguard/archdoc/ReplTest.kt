package org.archguard.archdoc

import io.kotest.matchers.shouldBe
import jupyter.kotlin.DependsOn
import org.jetbrains.kotlinx.jupyter.EvalRequestData
import org.jetbrains.kotlinx.jupyter.ReplForJupyterImpl
import org.jetbrains.kotlinx.jupyter.api.Code
import org.jetbrains.kotlinx.jupyter.libraries.EmptyResolutionInfoProvider
import org.jetbrains.kotlinx.jupyter.messaging.DisplayHandler
import org.junit.jupiter.api.Test
import kotlin.script.experimental.jvm.util.scriptCompilationClasspathFromContext

internal class DslTest {
    val classpath = scriptCompilationClasspathFromContext(
        "kotlin-jupyter-lib",
        "kotlin-jupyter-api",
        "kotlin-jupyter-shared-compiler",

        // kotlin libs
        "kotlin-stdlib",
        "kotlin-reflect",
        "kotlin-script-runtime",
        classLoader = DependsOn::class.java.classLoader
    )
    private val resolutionInfoProvider = EmptyResolutionInfoProvider
    private val repl = ReplForJupyterImpl(resolutionInfoProvider, classpath)

    private fun eval(code: Code, displayHandler: DisplayHandler? = null, jupyterId: Int = -1, storeHistory: Boolean = true) =
        repl.eval(EvalRequestData(code, displayHandler, jupyterId, storeHistory))

    @Test
    internal fun simple_layered() {
        eval("val x = 3")
        val res = eval("x*2")
        res.resultValue shouldBe 6
    }

}