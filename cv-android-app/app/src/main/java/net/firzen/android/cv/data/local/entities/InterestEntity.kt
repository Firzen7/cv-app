package net.firzen.android.cv.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A personal interest/hobby, e.g. "Photography", "Artificial Intelligence".
 */
@Entity(tableName = "interests")
data class InterestEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val language: String,

    val name: String
)
