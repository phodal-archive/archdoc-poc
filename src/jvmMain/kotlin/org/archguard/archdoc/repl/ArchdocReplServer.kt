package org.archguard.archdoc.repl

import kotlinx.serialization.Serializable
import org.jetbrains.kotlinx.jupyter.compiler.util.EvaluatedSnippetMetadata
import org.jetbrains.kotlinx.jupyter.repl.EvalResult

@Serializable
data class ReplResult(
    val resultValue: Any?,
    val metadata: EvaluatedSnippetMetadata = EvaluatedSnippetMetadata.EMPTY
) {

}

// todo: setup websocket server
class ArchdocReplServer {
    private val compiler: ArchdocCompiler = ArchdocCompiler()

    fun interpret(code: String, context: ReplContext) {
        runWithOutput(code, context.out)
    }

    private fun runWithOutput(code: String, out: ReplOutput) {
        this.eval(code)
    }

    fun eval(code: String): ReplResult {
        val result = compiler.eval(code)

        return ReplResult(
            result.resultValue,
            result.metadata
        )
        // todo: convert result
    }
}