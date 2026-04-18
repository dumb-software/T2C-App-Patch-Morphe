package com.dumbsoftware.t2c.tracking

import app.morphe.patcher.patch.rawResourcePatch
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.AppTarget

val COMPATIBILITY_T2C = Compatibility(
    name = "T2C",
    packageName = "fr.t2c.t2c",
    targets = listOf(
        AppTarget("3.2.0")
    )
)

@Suppress("unused")
val removeUmamiPatch = rawResourcePatch(
    name = "Remove Umami tracking",
    description = "Disables Umami tracking safely by modifying existing values."
) {
    compatibleWith(COMPATIBILITY_T2C)

    execute {
        listOf(".env.prod", ".env.dev").forEach { fileName ->
            val envFile = get("assets/flutter_assets/$fileName") ?: return@forEach
            
            val content = envFile.readText()
            val patched = content
                .replace(Regex("UMAMI_ENDPOINT=.*"), "UMAMI_ENDPOINT=http://127.0.0.1")
                .replace(Regex("UMAMI_WEBSITE=.*"), "UMAMI_WEBSITE=")
                .replace(Regex("UMAMI_HOSTNAME=.*"), "UMAMI_HOSTNAME=127.0.0.1")
                
            envFile.writeText(patched)
        }
    }
}