package com.glaze.knowledgebase.data.local.dao
import androidx.room.*
import com.glaze.knowledgebase.data.local.entity.GlazeTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GlazeTypeDao {
    @Query("SELECT * FROM glaze_types ORDER BY name") fun getAll(): Flow<List<GlazeTypeEntity>>
    @Query("SELECT * FROM glaze_types WHERE id = :id") fun getById(id: String): Flow<GlazeTypeEntity?>
    @Query("SELECT * FROM glaze_types WHERE id IN (:ids)") fun getByIds(ids: List<String>): Flow<List<GlazeTypeEntity>>
    @Query("SELECT * FROM glaze_types WHERE name LIKE '%' || :q || '%' OR description LIKE '%' || :q || '%'") fun search(q: String): Flow<List<GlazeTypeEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertAll(items: List<GlazeTypeEntity>)
    @Query("SELECT COUNT(*) FROM glaze_types") suspend fun getCount(): Int
}
