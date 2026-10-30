package com.dumbsoftware.t2c.features

import app.morphe.patcher.patch.resourcePatch
import com.dumbsoftware.t2c.COMPATIBILITY_T2C

@Suppress("unused")
val enableNetworkSecurityPatch = resourcePatch(
    name = "Enable network interception",
    description = "Enables cleartext HTTP traffic and user-installed certificate trust for debugging."
) {
    compatibleWith(COMPATIBILITY_T2C)

    execute {
        document("AndroidManifest.xml").use { document ->
            val appNodes = document.getElementsByTagName("application")
            if (appNodes.length > 0) {
                val appNode = appNodes.item(0)
                val attributes = appNode.attributes
                val usesCleartext = attributes.getNamedItem("android:usesCleartextTraffic")
                if (usesCleartext != null) {
                    usesCleartext.nodeValue = "true"
                } else {
                    val attr = document.createAttribute("android:usesCleartextTraffic")
                    attr.value = "true"
                    attributes.setNamedItem(attr)
                }
            }
        }
    }
}
