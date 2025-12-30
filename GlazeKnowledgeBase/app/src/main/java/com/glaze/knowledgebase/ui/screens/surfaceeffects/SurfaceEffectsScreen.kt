package com.glaze.knowledgebase.ui.screens.surfaceeffects
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.glaze.knowledgebase.data.repository.GlazeRepository
import com.glaze.knowledgebase.domain.model.SurfaceEffect
import com.glaze.knowledgebase.ui.components.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SurfaceEffectsViewModel @Inject constructor(private val repo: GlazeRepository) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()
    @OptIn(FlowPreview::class)
    val surfaceEffects: StateFlow<List<SurfaceEffect>> = _query.debounce(300).flatMapLatest { q -> if (q.isBlank()) repo.getAllSurfaceEffects() else repo.searchSurfaceEffects(q) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    fun onQueryChange(q: String) { _query.value = q }
}

@Composable
fun SurfaceEffectsScreen(navController: NavController, vm: SurfaceEffectsViewModel = hiltViewModel()) {
    val effects by vm.surfaceEffects.collectAsStateWithLifecycle()
    val query by vm.query.collectAsStateWithLifecycle()
    ItemListScreen("Yüzey Efektleri", effects, query, vm::onQueryChange, { navController.navigate("detail/surfaceeffect/${it.id}") }) { e ->
        Column(Modifier.padding(16.dp)) {
            Text(e.name, style = MaterialTheme.typography.titleMedium); Spacer(Modifier.height(4.dp))
            InfoChip(e.effectType.displayName); Spacer(Modifier.height(4.dp))
            Text(e.description, style = MaterialTheme.typography.bodySmall, maxLines = 2)
        }
    }
}
