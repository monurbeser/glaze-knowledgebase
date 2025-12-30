package com.glaze.knowledgebase.data.seed
import android.content.Context
import com.glaze.knowledgebase.data.local.GlazeDatabase
import com.glaze.knowledgebase.data.local.entity.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseSeeder @Inject constructor(
    @ApplicationContext private val context: Context,
    private val database: GlazeDatabase,
    private val gson: Gson
) {
    suspend fun seedIfEmpty() {
        if (database.materialDao().getCount() == 0) seed<MaterialEntity>("materials.json") { database.materialDao().insertAll(it) }
        if (database.colorantDao().getCount() == 0) seed<ColorantEntity>("colorants.json") { database.colorantDao().insertAll(it) }
        if (database.glazeTypeDao().getCount() == 0) seed<GlazeTypeEntity>("glaze_types.json") { database.glazeTypeDao().insertAll(it) }
        if (database.firingTypeDao().getCount() == 0) seed<FiringTypeEntity>("firing_types.json") { database.firingTypeDao().insertAll(it) }
        if (database.surfaceEffectDao().getCount() == 0) seed<SurfaceEffectEntity>("surface_effects.json") { database.surfaceEffectDao().insertAll(it) }
        if (database.safetyInfoDao().getCount() == 0) seed<SafetyInfoEntity>("safety_info.json") { database.safetyInfoDao().insertAll(it) }
        if (database.glossaryTermDao().getCount() == 0) seed<GlossaryTermEntity>("glossary_terms.json") { database.glossaryTermDao().insertAll(it) }
    }
    private suspend inline fun <reified T> seed(file: String, insert: (List<T>) -> Unit) {
        val json = context.assets.open("seed/$file").bufferedReader().use { it.readText() }
        val list: List<T> = gson.fromJson(json, object : TypeToken<List<T>>() {}.type)
        insert(list)
    }
}
