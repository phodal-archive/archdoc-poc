package org.archguard.archdoc.repl

import kotlinx.serialization.Serializable

@Serializable
data class ReplResult(
    var resultValue: String,
    var isArchdocApi: Boolean = false,
    var className: String = "",
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
        val replResult = ReplResult(
            result.resultValue.toString()
        )

        // todo: return callback action
        val className: String = result.resultValue?.javaClass?.name.orEmpty()
        if (className.startsWith("org.archguard.dsl")) {
            replResult.isArchdocApi = true
        }

        replResult.className = className

        return replResult
    }
}