package org.archguard.archdoc.repl

import kotlinx.serialization.Serializable

@Serializable
data class ReplResult(
    val resultValue: String
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
            result.resultValue.toString()
        )
    }
}