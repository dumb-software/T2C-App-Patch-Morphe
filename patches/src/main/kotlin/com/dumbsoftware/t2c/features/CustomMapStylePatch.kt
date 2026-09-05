package com.dumbsoftware.t2c.features

import app.morphe.patcher.patch.rawResourcePatch
import app.morphe.patcher.patch.stringOption
import com.dumbsoftware.t2c.COMPATIBILITY_T2C

@Suppress("unused")
val customMapStylePatch = rawResourcePatch(
    name = "Custom map style",
    description = "Replaces Google Maps styling with Dark Mode, AMOLED, or Retro theme."
) {
    compatibleWith(COMPATIBILITY_T2C)

    val mapTheme by stringOption(
        key = "map_theme",
        title = "Map Theme",
        description = "Select the Google Maps style to apply",
        default = "dark",
        values = mapOf(
            "Dark Mode" to "dark",
            "AMOLED Black" to "amoled",
            "Retro" to "retro"
        )
    )

    execute {
        val mapStyleFile = get("assets/flutter_assets/assets/maps_styles/maps_styles.json") ?: return@execute
        
        val darkStyle = """[
  {"elementType": "geometry", "stylers": [{"color": "#242f3e"}]},
  {"elementType": "labels.text.stroke", "stylers": [{"color": "#242f3e"}]},
  {"elementType": "labels.text.fill", "stylers": [{"color": "#746855"}]},
  {"featureType": "administrative.locality", "elementType": "labels.text.fill", "stylers": [{"color": "#d59563"}]},
  {"featureType": "poi", "elementType": "labels.text.fill", "stylers": [{"color": "#d59563"}]},
  {"featureType": "poi.park", "elementType": "geometry", "stylers": [{"color": "#263c3f"}]},
  {"featureType": "poi.park", "elementType": "labels.text.fill", "stylers": [{"color": "#6b9a76"}]},
  {"featureType": "road", "elementType": "geometry", "stylers": [{"color": "#38414e"}]},
  {"featureType": "road", "elementType": "geometry.stroke", "stylers": [{"color": "#212a37"}]},
  {"featureType": "road", "elementType": "labels.text.fill", "stylers": [{"color": "#9ca5b3"}]},
  {"featureType": "road.highway", "elementType": "geometry", "stylers": [{"color": "#746855"}]},
  {"featureType": "road.highway", "elementType": "geometry.stroke", "stylers": [{"color": "#1f2835"}]},
  {"featureType": "road.highway", "elementType": "labels.text.fill", "stylers": [{"color": "#f3d19c"}]},
  {"featureType": "transit", "elementType": "geometry", "stylers": [{"color": "#2f3948"}]},
  {"featureType": "transit.station", "elementType": "labels.text.fill", "stylers": [{"color": "#d59563"}]},
  {"featureType": "water", "elementType": "geometry", "stylers": [{"color": "#17263c"}]},
  {"featureType": "water", "elementType": "labels.text.fill", "stylers": [{"color": "#515c6d"}]},
  {"featureType": "water", "elementType": "labels.text.stroke", "stylers": [{"color": "#17263c"}]}
]"""

        val amoledStyle = """[
  {"elementType": "geometry", "stylers": [{"color": "#000000"}]},
  {"elementType": "labels.text.stroke", "stylers": [{"color": "#000000"}]},
  {"elementType": "labels.text.fill", "stylers": [{"color": "#8c8c8c"}]},
  {"featureType": "poi", "elementType": "labels.text.fill", "stylers": [{"color": "#606060"}]},
  {"featureType": "road", "elementType": "geometry", "stylers": [{"color": "#181818"}]},
  {"featureType": "road", "elementType": "labels.text.fill", "stylers": [{"color": "#a0a0a0"}]},
  {"featureType": "transit", "elementType": "geometry", "stylers": [{"color": "#121212"}]},
  {"featureType": "water", "elementType": "geometry", "stylers": [{"color": "#0a1118"}]}
]"""

        val retroStyle = """[
  {"elementType": "geometry", "stylers": [{"color": "#ebe3cd"}]},
  {"elementType": "labels.text.fill", "stylers": [{"color": "#523735"}]},
  {"elementType": "labels.text.stroke", "stylers": [{"color": "#f5f1e6"}]},
  {"featureType": "administrative", "elementType": "geometry.stroke", "stylers": [{"color": "#c9b2a6"}]},
  {"featureType": "poi", "elementType": "geometry", "stylers": [{"color": "#dfd2ae"}]},
  {"featureType": "poi.park", "elementType": "geometry.fill", "stylers": [{"color": "#a5b076"}]},
  {"featureType": "road", "elementType": "geometry", "stylers": [{"color": "#f5f1e6"}]},
  {"featureType": "road.highway", "elementType": "geometry", "stylers": [{"color": "#f8c967"}]},
  {"featureType": "transit.line", "elementType": "geometry", "stylers": [{"color": "#dfd2ae"}]},
  {"featureType": "water", "elementType": "geometry.fill", "stylers": [{"color": "#b9d3c2"}]}
]"""

        val selectedContent = when (mapTheme) {
            "amoled" -> amoledStyle
            "retro" -> retroStyle
            else -> darkStyle
        }

        mapStyleFile.writeText(selectedContent)
    }
}
