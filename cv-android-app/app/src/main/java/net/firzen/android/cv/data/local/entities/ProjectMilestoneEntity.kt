package net.firzen.android.cv.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * A milestone in a project's timeline (e.g., "2020: Discovery Mode feature").
 * Linked to a [ProjectEntity] via [projectId] with cascade delete — if a project
 * is removed, all its milestones are automatically deleted too.
 */
@Entity(
    tableName = "project_milestones",
    foreignKeys = [
        ForeignKey(
            entity = ProjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["project_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("project_id")]
)
data class ProjectMilestoneEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "project_id")
    val projectId: Int,

    val year: String,

    val title: String,

    val description: String,

    /** Controls the display order within the project — lower ordinal = earlier milestone. */
    val ordinal: Int
)
