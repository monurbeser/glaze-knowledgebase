package com.glaze.knowledgebase.data.local.dao
import androidx.room.*
import com.glaze.knowledgebase.data.local.entity.GlossaryTermEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GlossaryTermDao {
    @Query("SELECT * FROM glossary_terms ORDER BY term") fun getAll(): Flow<List<GlossaryTermEntity>>
    @Query("SELECT * FROM glossary_terms WHERE id = :id") fun getById(id: String): Flow<GlossaryTermEntity?>
    @Query("SELECT * FROM glossary_terms WHERE id IN (:ids)") fun getByIds(ids: List<String>): Flow<List<GlossaryTermEntity>>
    @Query("SELECT * FROM glossary_terms WHERE term LIKE '%' || :q || '%' OR definition LIKE '%' || :q || '%'") fun search(q: String): Flow<List<GlossaryTermEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertAll(items: List<GlossaryTermEntity>)
    @Query("SELECT COUNT(*) FROM glossary_terms") suspend fun getCount(): Int
}
