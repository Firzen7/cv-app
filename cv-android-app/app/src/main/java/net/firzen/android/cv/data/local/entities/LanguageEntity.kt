package net.firzen.android.cv.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A spoken/written language with proficiency level.
 * E.g., Czech (Native), English (~C1).
 */
@Entity(tableName = "languages")
data class LanguageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,

    /** Proficiency level, e.g. "Native", "Intermediate (~C1)". */
    val level: String,

    /** Optional note, e.g. "Fluent interaction with native speakers". */
    val note: String?
)
