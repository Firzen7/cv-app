package net.firzen.android.cv.domain.model

// Domain model for a project with its milestones embedded
data class Project(
    val id: Int,
    val name: String,
    val description: String,
    val googlePlayUrl: String?,
    val ordinal: Int,
    val milestones: List<ProjectMilestone> = emptyList()
)
