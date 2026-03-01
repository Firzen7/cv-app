package net.firzen.android.cv.domain.model

// Domain model for an education history entry
data class Education(
    val id: Int,
    val institution: String,
    val degree: String,
    val startYear: Int,
    val endYear: Int
)
