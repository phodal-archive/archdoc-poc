package org.archguard.archdoc.repl

import kotlinx.serialization.Serializable
import org.jetbrains.kotlinx.jupyter.compiler.util.EvaluatedSnippetMetadata

@Serializable
data class ReplResult(
    val resultValue: Any?,
    val metadata: EvaluatedSnippetMetadata = EvaluatedSnippetMetadata.EMPTY
)

// todo: setup websocket server
class ArchdocReplServer {
    private val compiler: ArchdocCompiler = ArchdocCompiler()

    fun interpret(code: String, context: ReplContext) {
        runWithOutput(code, context.out)
    }

    private fun runWithOutput(code: String, out: ReplOutput) {
        this.eval(code, 0)
    }

    fun eval(code: String, id: Int): ReplResult {
        val result = compiler.eval(code, null, id)

        return ReplResult(
            result.resultValue,
            result.metadata
        )
    }
}