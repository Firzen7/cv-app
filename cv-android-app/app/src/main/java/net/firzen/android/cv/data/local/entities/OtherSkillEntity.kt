package net.firzen.android.cv.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * A single skill within an [OtherSkillCategoryEntity].
 * E.g., "Git" in "Versioning", or "Jenkins" in "CI/CD".
 */
@Entity(
    tableName = "other_skills",
    foreignKeys = [
        ForeignKey(
            entity = OtherSkillCategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("category_id")]
)
data class OtherSkillEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val language: String,

    @ColumnInfo(name = "category_id")
    val categoryId: Int,

    val name: String,

    val description: String? = null
)
