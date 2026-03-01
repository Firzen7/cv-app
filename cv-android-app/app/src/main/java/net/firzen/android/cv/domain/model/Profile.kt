package net.firzen.android.cv.domain.model

// Domain model for personal profile information
data class Profile(
    val id: Int,
    val name: String,
    val title: String,
    val dateOfBirth: String,
    val address: String,
    val phone: String,
    val email: String,
    val linkedInUsername: String,
    val githubUsernames: String,
    val stackOverflowId: String
)
