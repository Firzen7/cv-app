package net.firzen.android.cv.domain.model

// Domain model for a technology category with its technologies embedded
data class TechnologyCategory(
    val id: Int,
    val name: String,
    val technologies: List<NamedItem> = emptyList()
)
