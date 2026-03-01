package net.firzen.android.cv.domain.model

// Domain model for a milestone in a project's timeline
data class ProjectMilestone(
    val id: Int,
    val projectId: Int,
    val year: String,
    val title: String,
    val description: String,
    val ordinal: Int
)
