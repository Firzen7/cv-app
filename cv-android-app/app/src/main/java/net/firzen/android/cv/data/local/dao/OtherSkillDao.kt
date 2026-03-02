package net.firzen.android.cv.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import net.firzen.android.cv.data.local.entities.OtherSkillCategoryEntity
import net.firzen.android.cv.data.local.entities.OtherSkillEntity

@Dao
interface OtherSkillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<OtherSkillCategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkills(skills: List<OtherSkillEntity>)

    @Query("SELECT * FROM other_skill_categories ORDER BY id ASC")
    fun getAllCategories(): Flow<List<OtherSkillCategoryEntity>>

    @Query("SELECT * FROM other_skills WHERE category_id = :categoryId ORDER BY id ASC")
    fun getForCategory(categoryId: Int): Flow<List<OtherSkillEntity>>

    @Query("SELECT * FROM other_skills ORDER BY category_id, id ASC")
    fun getAll(): Flow<List<OtherSkillEntity>>
}
