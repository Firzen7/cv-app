package net.firzen.android.cv.domain.model

// Domain model for a single work experience entry
data class WorkExperience(
    val id: Int,
    val company: String,
    val position: String,
    val startDate: String,
    val endDate: String?,
    val description: String,
    val ordinal: Int
)
