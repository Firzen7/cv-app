package net.firzen.android.cv.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.firzen.android.cv.data.local.entities.OtherSkillCategoryEntity
import net.firzen.android.cv.data.local.entities.OtherSkillEntity

@Dao
interface OtherSkillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<OtherSkillCategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkills(skills: List<OtherSkillEntity>)

    @Query("SELECT * FROM other_skill_categories ORDER BY id ASC")
    suspend fun getAllCategories(): List<OtherSkillCategoryEntity>

    @Query("SELECT * FROM other_skills WHERE category_id = :categoryId ORDER BY id ASC")
    suspend fun getForCategory(categoryId: Int): List<OtherSkillEntity>

    @Query("SELECT * FROM other_skills ORDER BY category_id, id ASC")
    suspend fun getAll(): List<OtherSkillEntity>
}
