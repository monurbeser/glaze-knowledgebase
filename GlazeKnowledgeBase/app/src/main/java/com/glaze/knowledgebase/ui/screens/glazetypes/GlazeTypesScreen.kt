package com.glaze.knowledgebase.ui.screens.glazetypes
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
import com.glaze.knowledgebase.domain.model.GlazeType
import com.glaze.knowledgebase.ui.components.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class GlazeTypesViewModel @Inject constructor(private val repo: GlazeRepository) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()
    @OptIn(FlowPreview::class)
    val glazeTypes: StateFlow<List<GlazeType>> = _query.debounce(300).flatMapLatest { q -> if (q.isBlank()) repo.getAllGlazeTypes() else repo.searchGlazeTypes(q) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    fun onQueryChange(q: String) { _query.value = q }
}

@Composable
fun GlazeTypesScreen(navController: NavController, vm: GlazeTypesViewModel = hiltViewModel()) {
    val glazeTypes by vm.glazeTypes.collectAsStateWithLifecycle()
    val query by vm.query.collectAsStateWithLifecycle()
    ItemListScreen("Sır Tipleri", glazeTypes, query, vm::onQueryChange, { navController.navigate("detail/glazetype/${it.id}") }) { g ->
        Column(Modifier.padding(16.dp)) {
            Text(g.name, style = MaterialTheme.typography.titleMedium); Spacer(Modifier.height(4.dp))
            InfoChip(g.category.displayName); Spacer(Modifier.height(4.dp))
            Text(g.description, style = MaterialTheme.typography.bodySmall, maxLines = 2)
            Text("Sıcaklık: ${g.typicalTemperatureRange}", style = MaterialTheme.typography.labelSmall)
        }
    }
}
