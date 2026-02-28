package net.firzen.android.cv.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A project created during work (WattsUp, Sanctuary First, etc.).
 * Each project can have multiple milestones, linked via [ProjectMilestoneEntity.projectId].
 */
@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,

    val description: String,

    @ColumnInfo(name = "google_play_url")
    val googlePlayUrl: String?,

    /** Controls the display order. */
    val ordinal: Int
)
