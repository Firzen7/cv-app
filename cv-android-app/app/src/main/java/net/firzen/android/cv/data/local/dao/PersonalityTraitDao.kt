package net.firzen.android.cv.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import net.firzen.android.cv.data.local.entities.PersonalityTraitEntity

@Dao
interface PersonalityTraitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(traits: List<PersonalityTraitEntity>)

    @Query("SELECT * FROM personality_traits ORDER BY id ASC")
    fun getAll(): Flow<List<PersonalityTraitEntity>>
}
