package com.dumbsoftware.t2c.tracking

import app.morphe.patcher.patch.resourcePatch
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.AppTarget

@Suppress("unused")
val removeFirebasePatch = resourcePatch(
    name = "Remove Firebase Tracking",
    description = "Removes Firebase App ID and Crashlytics API keys to disable Google tracking."
) {
    compatibleWith(COMPATIBILITY_T2C)

    execute {
        val stringsFile = get("res/values/strings.xml") ?: return@execute

        val content = stringsFile.readText()

        val patched = content.replace(
            Regex("<string name=\"(google_app_id|google_crash_reporting_api_key|google_api_key)\">.*?</string>"),
            "<string name=\"$1\"></string>"
        )

        stringsFile.writeText(patched)
    }
}
