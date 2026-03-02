package net.firzen.android.cv.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import net.firzen.android.cv.data.local.entities.TechnologyCategoryEntity
import net.firzen.android.cv.data.local.entities.TechnologyEntity

@Dao
interface TechnologyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<TechnologyCategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTechnologies(technologies: List<TechnologyEntity>)

    @Query("SELECT * FROM technology_categories WHERE language = :language ORDER BY id ASC")
    fun getAllCategories(language: String): Flow<List<TechnologyCategoryEntity>>

    @Query("SELECT * FROM technologies WHERE category_id = :categoryId AND language = :language ORDER BY id ASC")
    fun getForCategory(categoryId: Int, language: String): Flow<List<TechnologyEntity>>

    @Query("SELECT * FROM technologies WHERE language = :language ORDER BY category_id, id ASC")
    fun getAll(language: String): Flow<List<TechnologyEntity>>
}
