package com.glaze.knowledgebase.data.repository
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.glaze.knowledgebase.domain.model.ThemeMode
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class SettingsRepository @Inject constructor(@ApplicationContext private val context: Context) {
    private val themeModeKey = stringPreferencesKey("theme_mode")
    val themeMode: Flow<ThemeMode> = context.dataStore.data.map { ThemeMode.valueOf(it[themeModeKey] ?: ThemeMode.SYSTEM.name) }
    suspend fun setThemeMode(mode: ThemeMode) { context.dataStore.edit { it[themeModeKey] = mode.name } }
}
