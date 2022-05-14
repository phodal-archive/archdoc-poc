package org.archguard.archdoc.repl

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class ReplResult(
    var resultValue: String,
    var isArchdocApi: Boolean = false,
    var className: String = "",
    var actionData: String = "",
    var action: String = ""
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
        // todo: return error results
        val result = compiler.eval(code, null, id)
        val resultValue = result.resultValue
        val replResult = ReplResult(
            resultValue.toString()
        )

        // handle action data
        when(resultValue) {
            is org.archguard.dsl.Action -> {
                replResult.action = Json.encodeToString(resultValue)
                replResult.actionData = resultValue.data
            }
        }

        // todo: return callback action
        val className: String = resultValue?.javaClass?.name.orEmpty()
        if (className.startsWith("org.archguard.dsl")) {
            replResult.isArchdocApi = true
        }

        replResult.className = className

        return replResult
    }
}