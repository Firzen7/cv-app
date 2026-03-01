package net.firzen.android.cv.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.firzen.android.cv.data.local.entities.TechnologyCategoryEntity
import net.firzen.android.cv.data.local.entities.TechnologyEntity

@Dao
interface TechnologyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<TechnologyCategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTechnologies(technologies: List<TechnologyEntity>)

    @Query("SELECT * FROM technology_categories ORDER BY id ASC")
    suspend fun getAllCategories(): List<TechnologyCategoryEntity>

    @Query("SELECT * FROM technologies WHERE category_id = :categoryId ORDER BY id ASC")
    suspend fun getForCategory(categoryId: Int): List<TechnologyEntity>

    @Query("SELECT * FROM technologies ORDER BY category_id, id ASC")
    suspend fun getAll(): List<TechnologyEntity>
}
