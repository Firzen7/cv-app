package net.firzen.android.cv.domain.model

// Domain model for a spoken/written language with proficiency level
data class Language(
    val id: Int,
    val name: String,
    val level: String,
    val note: String?
)
