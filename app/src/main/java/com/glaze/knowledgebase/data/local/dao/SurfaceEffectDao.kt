package com.glaze.knowledgebase.data.local.dao
import androidx.room.*
import com.glaze.knowledgebase.data.local.entity.SurfaceEffectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SurfaceEffectDao {
    @Query("SELECT * FROM surface_effects ORDER BY name") fun getAll(): Flow<List<SurfaceEffectEntity>>
    @Query("SELECT * FROM surface_effects WHERE id = :id") fun getById(id: String): Flow<SurfaceEffectEntity?>
    @Query("SELECT * FROM surface_effects WHERE id IN (:ids)") fun getByIds(ids: List<String>): Flow<List<SurfaceEffectEntity>>
    @Query("SELECT * FROM surface_effects WHERE name LIKE '%' || :q || '%' OR description LIKE '%' || :q || '%'") fun search(q: String): Flow<List<SurfaceEffectEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertAll(items: List<SurfaceEffectEntity>)
    @Query("SELECT COUNT(*) FROM surface_effects") suspend fun getCount(): Int
}
