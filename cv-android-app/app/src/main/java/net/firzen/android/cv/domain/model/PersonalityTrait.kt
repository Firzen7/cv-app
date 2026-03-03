package net.firzen.android.cv.domain.model

// Domain model for a personality trait
data class PersonalityTrait(
    val id: Int,
    val trait: String,
    val description: String? = null
)
