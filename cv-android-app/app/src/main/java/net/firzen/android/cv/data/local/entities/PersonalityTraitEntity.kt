package net.firzen.android.cv.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A personality trait, e.g. "Independence", "Honesty".
 */
@Entity(tableName = "personality_traits")
data class PersonalityTraitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val language: String,

    val trait: String
)
