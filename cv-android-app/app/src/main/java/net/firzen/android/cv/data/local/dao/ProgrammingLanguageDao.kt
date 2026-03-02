package net.firzen.android.cv.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import net.firzen.android.cv.data.local.entities.ProgrammingLanguageEntity

@Dao
interface ProgrammingLanguageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(languages: List<ProgrammingLanguageEntity>)

    /** Returns programming languages ordered by skill level (highest first). */
    @Query("SELECT * FROM programming_languages WHERE language = :language ORDER BY level DESC")
    fun getAll(language: String): Flow<List<ProgrammingLanguageEntity>>
}
