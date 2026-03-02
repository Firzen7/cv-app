package net.firzen.android.cv.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Education history entry (university degree, high school, etc.).
 */
@Entity(tableName = "education")
data class EducationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val language: String,

    val institution: String,

    val degree: String,

    @ColumnInfo(name = "start_year")
    val startYear: Int,

    @ColumnInfo(name = "end_year")
    val endYear: Int
)
