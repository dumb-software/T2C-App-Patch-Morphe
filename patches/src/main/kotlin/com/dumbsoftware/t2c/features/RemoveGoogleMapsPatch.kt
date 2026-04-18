package com.dumbsoftware.t2c.features

import app.morphe.patcher.patch.resourcePatch
import com.dumbsoftware.t2c.tracking.COMPATIBILITY_T2C

@Suppress("unused")
val removeGoogleMapsPatch = resourcePatch(
    name = "Remove Google Maps API Key",
    description = "Removes the Google Maps API Key from the manifest (Breaks the interactive map but stops Google tracking)."
) {
    compatibleWith(COMPATIBILITY_T2C)

    execute {
        document("AndroidManifest.xml").use { document ->
            val metaDataNodes = document.getElementsByTagName("meta-data")
            for (i in 0 until metaDataNodes.length) {
                val node = metaDataNodes.item(i)
                val nameAttr = node.attributes.getNamedItem("android:name")
                if (nameAttr?.nodeValue == "com.google.android.geo.API_KEY") {
                    node.attributes.getNamedItem("android:value")?.nodeValue = ""
                }
            }
        }
    }
}
