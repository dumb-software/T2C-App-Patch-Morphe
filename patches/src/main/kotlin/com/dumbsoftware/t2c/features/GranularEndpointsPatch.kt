package com.dumbsoftware.t2c.features

import app.morphe.patcher.patch.rawResourcePatch
import app.morphe.patcher.patch.stringOption
import com.dumbsoftware.t2c.COMPATIBILITY_T2C
import com.dumbsoftware.t2c.util.updateEnvVariables

@Suppress("unused")
val granularEndpointsPatch = rawResourcePatch(
    name = "Custom microservices endpoints",
    description = "Points individual microservices to custom server URLs."
) {
    compatibleWith(COMPATIBILITY_T2C)

    val customSiv by stringOption(
        key = "siv_url",
        title = "SIV URL",
        description = "URL for the SIV passenger information service",
        default = "https://api.t2c.fr/siv"
    )

    val customEditorial by stringOption(
        key = "editorial_url",
        title = "Editorial URL",
        description = "URL for the Editorial & Traffic alerts service",
        default = "https://api.t2c.fr/editorial"
    )

    val customNotifications by stringOption(
        key = "notifications_url",
        title = "Notifications URL",
        description = "URL for the Push Notifications service",
        default = "https://api.t2c.fr/notification"
    )

    val customFavorites by stringOption(
        key = "favorites_url",
        title = "Favorites URL",
        description = "URL for the User Favorites service",
        default = "https://api.t2c.fr/favorite"
    )

    val customStatus by stringOption(
        key = "status_url",
        title = "Status URL",
        description = "URL for the Status & Maintenance service",
        default = "https://api.t2c.fr/status"
    )

    execute {
        val updates = mutableMapOf<String, String>()
        customSiv?.let { updates["URL_SIV"] = it }
        customEditorial?.let { updates["URL_EDITORIAL"] = it }
        customNotifications?.let { updates["URL_NOTIFICATIONS"] = it }
        customFavorites?.let { updates["URL_FAVORITES"] = it }
        customStatus?.let { updates["URL_STATUS"] = it }

        if (updates.isNotEmpty()) {
            updateEnvVariables(
                fileNames = listOf(".env.prod", ".env.dev"),
                updates = updates
            )
        }
    }
}
