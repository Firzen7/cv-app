package net.firzen.android.cv.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import net.firzen.android.cv.data.local.entities.EducationEntity

@Dao
interface EducationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(education: List<EducationEntity>)

    @Query("SELECT * FROM education ORDER BY start_year DESC")
    fun getAll(): Flow<List<EducationEntity>>
}
