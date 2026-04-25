package com.dumbsoftware.t2c.features

import app.morphe.patcher.patch.rawResourcePatch
import app.morphe.patcher.patch.stringOption
import com.dumbsoftware.t2c.COMPATIBILITY_T2C

@Suppress("unused")
val customEndpointPatch = rawResourcePatch(
    name = "Change API endpoint",
    description = "Points the app to a custom backend server by modifying environment variables."
) {
    compatibleWith(COMPATIBILITY_T2C)

    val customUrl by stringOption(
        key = "api_url",
        title = "API URL",
        description = "Enter the base URL for the API (e.g., https://my-proxy.t2c.fr)",
        default = "https://api.t2c.fr"
    )

    execute {
        val envFile = get("assets/flutter_assets/.env.prod") ?: return@execute
        val content = envFile.readText()

        val patched = content.replace("https://api.t2c.fr", customUrl ?: "https://api.t2c.fr")
        
        envFile.writeText(patched)
    }
}