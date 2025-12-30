package com.glaze.knowledgebase.data.local.dao
import androidx.room.*
import com.glaze.knowledgebase.data.local.entity.ColorantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorantDao {
    @Query("SELECT * FROM colorants ORDER BY name") fun getAll(): Flow<List<ColorantEntity>>
    @Query("SELECT * FROM colorants WHERE id = :id") fun getById(id: String): Flow<ColorantEntity?>
    @Query("SELECT * FROM colorants WHERE id IN (:ids)") fun getByIds(ids: List<String>): Flow<List<ColorantEntity>>
    @Query("SELECT * FROM colorants WHERE name LIKE '%' || :q || '%' OR description LIKE '%' || :q || '%'") fun search(q: String): Flow<List<ColorantEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertAll(items: List<ColorantEntity>)
    @Query("SELECT COUNT(*) FROM colorants") suspend fun getCount(): Int
}
