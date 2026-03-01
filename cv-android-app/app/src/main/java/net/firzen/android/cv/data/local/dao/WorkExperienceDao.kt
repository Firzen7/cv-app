package net.firzen.android.cv.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.firzen.android.cv.data.local.entities.WorkExperienceEntity

@Dao
interface WorkExperienceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(experiences: List<WorkExperienceEntity>)

    /** Returns all work experiences ordered by ordinal (most recent first). */
    @Query("SELECT * FROM work_experience ORDER BY ordinal ASC")
    suspend fun getAll(): List<WorkExperienceEntity>
}
