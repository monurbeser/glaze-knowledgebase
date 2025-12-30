package com.glaze.knowledgebase.data.local.dao
import androidx.room.*
import com.glaze.knowledgebase.data.local.entity.MaterialEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MaterialDao {
    @Query("SELECT * FROM materials ORDER BY name") fun getAll(): Flow<List<MaterialEntity>>
    @Query("SELECT * FROM materials WHERE id = :id") fun getById(id: String): Flow<MaterialEntity?>
    @Query("SELECT * FROM materials WHERE id IN (:ids)") fun getByIds(ids: List<String>): Flow<List<MaterialEntity>>
    @Query("SELECT * FROM materials WHERE name LIKE '%' || :q || '%' OR description LIKE '%' || :q || '%'") fun search(q: String): Flow<List<MaterialEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertAll(items: List<MaterialEntity>)
    @Query("SELECT COUNT(*) FROM materials") suspend fun getCount(): Int
}
