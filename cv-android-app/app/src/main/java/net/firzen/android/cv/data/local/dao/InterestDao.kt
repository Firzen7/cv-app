package net.firzen.android.cv.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import net.firzen.android.cv.data.local.entities.InterestEntity

@Dao
interface InterestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(interests: List<InterestEntity>)

    @Query("SELECT * FROM interests ORDER BY id ASC")
    fun getAll(): Flow<List<InterestEntity>>
}
