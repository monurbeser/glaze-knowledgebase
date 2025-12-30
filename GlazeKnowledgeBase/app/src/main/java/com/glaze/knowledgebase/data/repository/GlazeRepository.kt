package com.glaze.knowledgebase.data.repository
import com.glaze.knowledgebase.data.local.GlazeDatabase
import com.glaze.knowledgebase.data.local.toDomain
import com.glaze.knowledgebase.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlazeRepository @Inject constructor(private val db: GlazeDatabase) {
    fun getAllMaterials(): Flow<List<Material>> = db.materialDao().getAll().map { l -> l.map { it.toDomain() } }
    fun getMaterialById(id: String): Flow<Material?> = db.materialDao().getById(id).map { it?.toDomain() }
    fun searchMaterials(q: String): Flow<List<Material>> = db.materialDao().search(q).map { l -> l.map { it.toDomain() } }
    fun getMaterialsByIds(ids: List<String>): Flow<List<Material>> = db.materialDao().getByIds(ids).map { l -> l.map { it.toDomain() } }

    fun getAllColorants(): Flow<List<Colorant>> = db.colorantDao().getAll().map { l -> l.map { it.toDomain() } }
    fun getColorantById(id: String): Flow<Colorant?> = db.colorantDao().getById(id).map { it?.toDomain() }
    fun searchColorants(q: String): Flow<List<Colorant>> = db.colorantDao().search(q).map { l -> l.map { it.toDomain() } }
    fun getColorantsByIds(ids: List<String>): Flow<List<Colorant>> = db.colorantDao().getByIds(ids).map { l -> l.map { it.toDomain() } }

    fun getAllGlazeTypes(): Flow<List<GlazeType>> = db.glazeTypeDao().getAll().map { l -> l.map { it.toDomain() } }
    fun getGlazeTypeById(id: String): Flow<GlazeType?> = db.glazeTypeDao().getById(id).map { it?.toDomain() }
    fun searchGlazeTypes(q: String): Flow<List<GlazeType>> = db.glazeTypeDao().search(q).map { l -> l.map { it.toDomain() } }
    fun getGlazeTypesByIds(ids: List<String>): Flow<List<GlazeType>> = db.glazeTypeDao().getByIds(ids).map { l -> l.map { it.toDomain() } }

    fun getAllFiringTypes(): Flow<List<FiringType>> = db.firingTypeDao().getAll().map { l -> l.map { it.toDomain() } }
    fun getFiringTypeById(id: String): Flow<FiringType?> = db.firingTypeDao().getById(id).map { it?.toDomain() }
    fun searchFiringTypes(q: String): Flow<List<FiringType>> = db.firingTypeDao().search(q).map { l -> l.map { it.toDomain() } }
    fun getFiringTypesByIds(ids: List<String>): Flow<List<FiringType>> = db.firingTypeDao().getByIds(ids).map { l -> l.map { it.toDomain() } }

    fun getAllSurfaceEffects(): Flow<List<SurfaceEffect>> = db.surfaceEffectDao().getAll().map { l -> l.map { it.toDomain() } }
    fun getSurfaceEffectById(id: String): Flow<SurfaceEffect?> = db.surfaceEffectDao().getById(id).map { it?.toDomain() }
    fun searchSurfaceEffects(q: String): Flow<List<SurfaceEffect>> = db.surfaceEffectDao().search(q).map { l -> l.map { it.toDomain() } }
    fun getSurfaceEffectsByIds(ids: List<String>): Flow<List<SurfaceEffect>> = db.surfaceEffectDao().getByIds(ids).map { l -> l.map { it.toDomain() } }

    fun getAllSafetyInfo(): Flow<List<SafetyInfo>> = db.safetyInfoDao().getAll().map { l -> l.map { it.toDomain() } }
    fun getSafetyInfoById(id: String): Flow<SafetyInfo?> = db.safetyInfoDao().getById(id).map { it?.toDomain() }
    fun searchSafetyInfo(q: String): Flow<List<SafetyInfo>> = db.safetyInfoDao().search(q).map { l -> l.map { it.toDomain() } }

    fun getAllGlossaryTerms(): Flow<List<GlossaryTerm>> = db.glossaryTermDao().getAll().map { l -> l.map { it.toDomain() } }
    fun getGlossaryTermById(id: String): Flow<GlossaryTerm?> = db.glossaryTermDao().getById(id).map { it?.toDomain() }
    fun searchGlossaryTerms(q: String): Flow<List<GlossaryTerm>> = db.glossaryTermDao().search(q).map { l -> l.map { it.toDomain() } }
    fun getGlossaryTermsByIds(ids: List<String>): Flow<List<GlossaryTerm>> = db.glossaryTermDao().getByIds(ids).map { l -> l.map { it.toDomain() } }
}
