package com.glaze.knowledgebase.data.local.dao
import androidx.room.*
import com.glaze.knowledgebase.data.local.entity.FiringTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FiringTypeDao {
    @Query("SELECT * FROM firing_types ORDER BY name") fun getAll(): Flow<List<FiringTypeEntity>>
    @Query("SELECT * FROM firing_types WHERE id = :id") fun getById(id: String): Flow<FiringTypeEntity?>
    @Query("SELECT * FROM firing_types WHERE id IN (:ids)") fun getByIds(ids: List<String>): Flow<List<FiringTypeEntity>>
    @Query("SELECT * FROM firing_types WHERE name LIKE '%' || :q || '%' OR description LIKE '%' || :q || '%'") fun search(q: String): Flow<List<FiringTypeEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertAll(items: List<FiringTypeEntity>)
    @Query("SELECT COUNT(*) FROM firing_types") suspend fun getCount(): Int
}
