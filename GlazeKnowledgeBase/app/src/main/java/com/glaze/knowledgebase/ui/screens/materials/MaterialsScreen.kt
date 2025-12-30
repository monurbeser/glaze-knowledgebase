package com.glaze.knowledgebase.ui.screens.materials
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
import com.glaze.knowledgebase.domain.model.Material
import com.glaze.knowledgebase.ui.components.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MaterialsViewModel @Inject constructor(private val repo: GlazeRepository) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()
    @OptIn(FlowPreview::class)
    val materials: StateFlow<List<Material>> = _query.debounce(300).flatMapLatest { q -> if (q.isBlank()) repo.getAllMaterials() else repo.searchMaterials(q) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    fun onQueryChange(q: String) { _query.value = q }
}

@Composable
fun MaterialsScreen(navController: NavController, vm: MaterialsViewModel = hiltViewModel()) {
    val materials by vm.materials.collectAsStateWithLifecycle()
    val query by vm.query.collectAsStateWithLifecycle()
    ItemListScreen("Hammaddeler", materials, query, vm::onQueryChange, { navController.navigate("detail/material/${it.id}") }) { m ->
        Column(Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) { Text(m.name, style = MaterialTheme.typography.titleMedium); SafetyBadge(m.safetyLevel) }
            Spacer(Modifier.height(4.dp)); InfoChip(m.category.displayName); Spacer(Modifier.height(4.dp))
            Text(m.description, style = MaterialTheme.typography.bodySmall, maxLines = 2)
        }
    }
}
