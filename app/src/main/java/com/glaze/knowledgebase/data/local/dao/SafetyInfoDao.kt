package com.glaze.knowledgebase.data.local.dao
import androidx.room.*
import com.glaze.knowledgebase.data.local.entity.SafetyInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SafetyInfoDao {
    @Query("SELECT * FROM safety_info ORDER BY title") fun getAll(): Flow<List<SafetyInfoEntity>>
    @Query("SELECT * FROM safety_info WHERE id = :id") fun getById(id: String): Flow<SafetyInfoEntity?>
    @Query("SELECT * FROM safety_info WHERE title LIKE '%' || :q || '%' OR description LIKE '%' || :q || '%'") fun search(q: String): Flow<List<SafetyInfoEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertAll(items: List<SafetyInfoEntity>)
    @Query("SELECT COUNT(*) FROM safety_info") suspend fun getCount(): Int
}
