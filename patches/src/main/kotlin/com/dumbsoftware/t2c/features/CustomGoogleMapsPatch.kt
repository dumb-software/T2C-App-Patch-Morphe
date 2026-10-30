package com.dumbsoftware.t2c.features

import app.morphe.patcher.patch.resourcePatch
import app.morphe.patcher.patch.stringOption
import com.dumbsoftware.t2c.COMPATIBILITY_T2C

@Suppress("unused")
val customGoogleMapsPatch = resourcePatch(
    name = "Custom Google Maps API key",
    description = "Replaces the Google Maps API key in the manifest with a custom one."
) {
    compatibleWith(COMPATIBILITY_T2C)

    val apiKey by stringOption(
        key = "maps_api_key",
        title = "Google Maps API Key",
        description = "Enter your custom Google Maps Android API key",
        default = ""
    )

    execute {
        document("AndroidManifest.xml").use { document ->
            val metaDataNodes = document.getElementsByTagName("meta-data")
            (0 until metaDataNodes.length).forEach { i ->
                val node = metaDataNodes.item(i)
                val attributes = node.attributes
                if (attributes.getNamedItem("android:name")?.nodeValue == "com.google.android.geo.API_KEY") {
                    attributes.getNamedItem("android:value")?.let { it.nodeValue = apiKey ?: "" }
                }
            }
        }
    }
}
