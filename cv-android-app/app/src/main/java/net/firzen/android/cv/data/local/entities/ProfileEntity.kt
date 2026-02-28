package net.firzen.android.cv.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Personal profile information — name, contact details, social links.
 * This table always contains exactly one row (the CV owner's data).
 */
@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey
    val id: Int = 1,

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
