package net.firzen.android.cv.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A category that groups related technologies together.
 * E.g., "UI", "Networking", "Data", "Architecture".
 *
 * Individual technologies within each category are stored in [TechnologyEntity].
 */
@Entity(tableName = "technology_categories")
data class TechnologyCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "category_name")
    val categoryName: String
)
