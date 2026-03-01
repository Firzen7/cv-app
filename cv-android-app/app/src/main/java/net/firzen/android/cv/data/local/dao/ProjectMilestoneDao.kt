package net.firzen.android.cv.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.firzen.android.cv.data.local.entities.ProjectMilestoneEntity

@Dao
interface ProjectMilestoneDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(milestones: List<ProjectMilestoneEntity>)

    /** Returns milestones for a specific project, ordered chronologically. */
    @Query("SELECT * FROM project_milestones WHERE project_id = :projectId ORDER BY ordinal ASC")
    suspend fun getForProject(projectId: Int): List<ProjectMilestoneEntity>

    @Query("SELECT * FROM project_milestones ORDER BY project_id, ordinal ASC")
    suspend fun getAll(): List<ProjectMilestoneEntity>
}
