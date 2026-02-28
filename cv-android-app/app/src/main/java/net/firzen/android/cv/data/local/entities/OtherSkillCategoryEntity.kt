package net.firzen.android.cv.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A category for "Other skills and knowledge" section.
 * E.g., "Versioning", "CI/CD", "Backend", "Infrastructure".
 *
 * Individual skills within each category are stored in [OtherSkillEntity].
 */
@Entity(tableName = "other_skill_categories")
data class OtherSkillCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "category_name")
    val categoryName: String
)
