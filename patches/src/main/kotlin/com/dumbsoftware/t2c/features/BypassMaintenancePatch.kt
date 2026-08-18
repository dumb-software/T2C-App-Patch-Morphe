package com.dumbsoftware.t2c.features

import app.morphe.patcher.patch.rawResourcePatch
import com.dumbsoftware.t2c.COMPATIBILITY_T2C
import com.dumbsoftware.t2c.util.updateEnvVariables

@Suppress("unused")
val bypassMaintenancePatch = rawResourcePatch(
    name = "Bypass maintenance and force update",
    description = "Disables maintenance lock and force-update screens by overriding the status endpoint."
) {
    compatibleWith(COMPATIBILITY_T2C)

    execute {
        updateEnvVariables(
            fileNames = listOf(".env.prod", ".env.dev"),
            updates = mapOf(
                "URL_STATUS" to "http://127.0.0.1:0"
            )
        )
    }
}
