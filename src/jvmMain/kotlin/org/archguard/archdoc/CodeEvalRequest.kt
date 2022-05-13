package org.archguard.archdoc

import kotlinx.serialization.Serializable

@Serializable
data class CodeEvalRequest(val code: String) {}