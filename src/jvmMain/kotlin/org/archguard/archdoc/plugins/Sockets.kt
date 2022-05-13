package org.archguard.archdoc.plugins

import org.archguard.archdoc.Connection
import io.ktor.websocket.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.archguard.archdoc.repl.ArchdocReplServer
import org.archguard.archdoc.repl.ReplContext
import java.time.*
import java.util.*
import kotlin.collections.LinkedHashSet

fun Application.configureSockets() {
    val replServer = ArchdocReplServer()
    val context = ReplContext()

    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    routing {
        val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
        webSocket("/") {
            val thisConnection = Connection(this)
            connections += thisConnection
            try {
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText = frame.readText()
                    connections.forEach {
                        val content = replServer.eval(receivedText).toString()
                        it.session.send(Json.encodeToString(content))
                    }
                }
            } catch (e: Exception) {
                println(e.localizedMessage)
            } finally {
                println("Removing $thisConnection!")
                connections -= thisConnection
            }
        }
    }
}