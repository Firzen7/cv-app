package net.firzen.android.cv.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import net.firzen.android.cv.data.local.entities.LanguageEntity

@Dao
interface LanguageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(languages: List<LanguageEntity>)

    @Query("SELECT * FROM languages WHERE language = :language ORDER BY id ASC")
    fun getAll(language: String): Flow<List<LanguageEntity>>
}
