package net.firzen.android.cv.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * Personal profile information — name, contact details, social links.
 * One row per language (e.g., "en" and "cs").
 */
@Entity(tableName = "profile", primaryKeys = ["id", "language"])
data class ProfileEntity(
    val id: Int = 1,

    val language: String,

    val name: String,

    val title: String,

    @ColumnInfo(name = "date_of_birth")
    val dateOfBirth: String,

    val address: String,

    val phone: String,

    val email: String,

    @ColumnInfo(name = "linkedin_username")
    val linkedInUsername: String,

    @ColumnInfo(name = "github_usernames")
    val githubUsernames: String,

    @ColumnInfo(name = "stackoverflow_id")
    val stackOverflowId: String
)
