package net.firzen.android.cv.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A single work experience entry (job position).
 * Ordered by [ordinal] to control display order in the timeline.
 */
@Entity(tableName = "work_experience")
data class WorkExperienceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val language: String,

    val company: String,

    val position: String,

    @ColumnInfo(name = "start_date")
    val startDate: String,

    @ColumnInfo(name = "end_date")
    val endDate: String?,

    val description: String,

    /** Controls the display order — lower ordinal = shown first (most recent). */
    val ordinal: Int
)
