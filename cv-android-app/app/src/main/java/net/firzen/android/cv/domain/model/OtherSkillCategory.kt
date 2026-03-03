package net.firzen.android.cv.domain.model

// Domain model for an "other skill" category with its skills embedded
data class OtherSkillCategory(
    val id: Int,
    val name: String,
    val skills: List<NamedItem> = emptyList()
)
