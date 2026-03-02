package net.firzen.android.cv.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import net.firzen.android.cv.data.local.entities.ProjectEntity

@Dao
interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(projects: List<ProjectEntity>)

    @Query("SELECT * FROM projects WHERE language = :language ORDER BY ordinal ASC")
    fun getAll(language: String): Flow<List<ProjectEntity>>

    @Query("SELECT * FROM projects WHERE id = :projectId AND language = :language")
    fun getById(projectId: Int, language: String): Flow<ProjectEntity?>
}
