package net.firzen.android.cv.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A programming language with a skill level (1–5 scale).
 * E.g., Kotlin 5/5, Java 5/5, Bash 4/5.
 */
@Entity(tableName = "programming_languages")
data class ProgrammingLanguageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,

    /** Skill level from 1 (beginner) to 5 (expert). */
    val level: Int
)
