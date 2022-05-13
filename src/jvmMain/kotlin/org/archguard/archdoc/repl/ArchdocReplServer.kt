package org.archguard.archdoc.repl

// todo: setup websocket server
class ArchdocReplServer {
    private val compiler: ArchdocCompiler = ArchdocCompiler()

    fun interpret(code: String, context: ReplContext) {

        runWithOutput(code, context.out)
    }

    private fun runWithOutput(code: String, out: ReplOutput) {
        this.eval(code)
    }

    private fun eval(code: String) {
        compiler.eval(code)
        // todo: convert result
    }
}