package com.dumbsoftware.t2c.tracking

import app.morphe.patcher.patch.resourcePatch
import com.dumbsoftware.t2c.COMPATIBILITY_T2C

@Suppress("unused")
val removeFirebasePatch = resourcePatch(
    name = "Remove Firebase tracking",
    description = "Removes Firebase App ID and Crashlytics API keys to disable Google tracking."
) {
    compatibleWith(COMPATIBILITY_T2C)

    execute {
        document("res/values/strings.xml").use { document ->
            val stringNodes = document.getElementsByTagName("string")
            for (i in 0 until stringNodes.length) {
                val node = stringNodes.item(i)
                val nameAttr = node.attributes.getNamedItem("name")?.nodeValue
                if (nameAttr == "google_app_id" || nameAttr == "google_crash_reporting_api_key" || nameAttr == "google_api_key") {
                    node.textContent = ""
                }
            }
        }
    }
}