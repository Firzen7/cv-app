package net.firzen.android.cv.domain.model

// Domain model for a personal interest/hobby
data class Interest(
    val id: Int,
    val name: String,
    val description: String? = null
)
