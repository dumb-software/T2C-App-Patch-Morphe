package com.dumbsoftware.t2c.features

import app.morphe.patcher.patch.rawResourcePatch
import com.dumbsoftware.t2c.tracking.COMPATIBILITY_T2C

@Suppress("unused")
val forceDevEnvironmentPatch = rawResourcePatch(
    name = "Force Developer Environment (Staging)",
    description = "Replaces the production environment variables with the developer/staging ones."
) {
    compatibleWith(COMPATIBILITY_T2C)

    execute {
        val prodEnv = get("assets/flutter_assets/.env.prod") ?: return@execute
        val devEnv = get("assets/flutter_assets/.env.dev") ?: return@execute

        prodEnv.writeText(devEnv.readText())
    }
}
