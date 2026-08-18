package com.dumbsoftware.t2c.features

import app.morphe.patcher.patch.resourcePatch
import com.dumbsoftware.t2c.COMPATIBILITY_T2C

@Suppress("unused")
val disableBackgroundServicePatch = resourcePatch(
    name = "Disable background location service",
    description = "Disables persistent background location service and wake locks to save battery."
) {
    compatibleWith(COMPATIBILITY_T2C)

    execute {
        document("AndroidManifest.xml").use { document ->
            val manifestNodes = document.getElementsByTagName("manifest")
            val manifestElement = if (manifestNodes.length > 0) manifestNodes.item(0) else null

            // Disable background service
            val serviceNodes = document.getElementsByTagName("service")
            (0 until serviceNodes.length).forEach { i ->
                val service = serviceNodes.item(i)
                val nameAttr = service?.attributes?.getNamedItem("android:name")?.nodeValue
                if (nameAttr == "com.baseflow.geolocator.GeolocatorLocationService") {
                    service.attributes?.getNamedItem("android:enabled")?.let { it.nodeValue = "false" }
                }
            }

            // Remove wake lock and boot permissions if present
            val permissionNodes = document.getElementsByTagName("uses-permission")
            val permissionsToRemove = mutableListOf<org.w3c.dom.Node>()
            (0 until permissionNodes.length).forEach { i ->
                val perm = permissionNodes.item(i)
                val permName = perm.attributes?.getNamedItem("android:name")?.nodeValue
                if (permName in setOf("android.permission.WAKE_LOCK", "android.permission.RECEIVE_BOOT_COMPLETED")) {
                    permissionsToRemove.add(perm)
                }
            }
            permissionsToRemove.forEach { p ->
                manifestElement?.removeChild(p)
            }
        }
    }
}
