package net.firzen.android.cv.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import net.firzen.android.cv.data.local.entities.ProjectMilestoneEntity

@Dao
interface ProjectMilestoneDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(milestones: List<ProjectMilestoneEntity>)

    /** Returns milestones for a specific project, ordered chronologically. */
    @Query("SELECT * FROM project_milestones WHERE project_id = :projectId AND language = :language ORDER BY ordinal ASC")
    fun getForProject(projectId: Int, language: String): Flow<List<ProjectMilestoneEntity>>

    @Query("SELECT * FROM project_milestones WHERE language = :language ORDER BY project_id, ordinal ASC")
    fun getAll(language: String): Flow<List<ProjectMilestoneEntity>>
}
