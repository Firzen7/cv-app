package net.firzen.android.cv.domain.model

/**
 * A named item with an optional description.
 * Used by [TechnologyCategory] and [OtherSkillCategory] to carry
 * both a display name and a detail text for chip dialogs.
 */
data class NamedItem(
    val name: String,
    val description: String? = null
)
