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

    @Test
    internal fun simple_eval() {
        val repl = makeEmbeddedRepl()

        fun eval(
            code: Code,
            displayHandler: DisplayHandler? = null,
            jupyterId: Int = -1,
            storeHistory: Boolean = true
        ) =
            repl.eval(EvalRequestData(code, displayHandler, jupyterId, storeHistory))

        eval("val x = 3")
        val res = eval("x*2")
        res.resultValue shouldBe 6
    }

}