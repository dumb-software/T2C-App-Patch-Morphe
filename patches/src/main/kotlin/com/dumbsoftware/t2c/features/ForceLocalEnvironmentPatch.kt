package com.dumbsoftware.t2c.features

import app.morphe.patcher.patch.rawResourcePatch
import com.dumbsoftware.t2c.COMPATIBILITY_T2C

@Suppress("unused")
val forceLocalEnvironmentPatch = rawResourcePatch(
    name = "Force local environment",
    description = "Replaces the production environment variables with the local ones."
) {
    compatibleWith(COMPATIBILITY_T2C)

    execute {
        val prodEnv = get("assets/flutter_assets/.env.prod") ?: return@execute
        val localEnv = get("assets/flutter_assets/.env.local") ?: return@execute

        prodEnv.writeText(localEnv.readText())
    }
}