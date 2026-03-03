package net.firzen.android.cv.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * A single technology/tool within a [TechnologyCategoryEntity].
 * E.g., "Jetpack Compose" in the "UI" category, or "Retrofit" in "Networking".
 */
@Entity(
    tableName = "technologies",
    foreignKeys = [
        ForeignKey(
            entity = TechnologyCategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("category_id")]
)
data class TechnologyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val language: String,

    @ColumnInfo(name = "category_id")
    val categoryId: Int,

    val name: String,

    val description: String? = null
)
