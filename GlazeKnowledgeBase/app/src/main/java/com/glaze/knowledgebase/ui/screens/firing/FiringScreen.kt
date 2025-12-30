package com.glaze.knowledgebase.ui.screens.firing
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
import com.glaze.knowledgebase.domain.model.FiringType
import com.glaze.knowledgebase.ui.components.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class FiringViewModel @Inject constructor(private val repo: GlazeRepository) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()
    @OptIn(FlowPreview::class)
    val firingTypes: StateFlow<List<FiringType>> = _query.debounce(300).flatMapLatest { q -> if (q.isBlank()) repo.getAllFiringTypes() else repo.searchFiringTypes(q) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    fun onQueryChange(q: String) { _query.value = q }
}

@Composable
fun FiringScreen(navController: NavController, vm: FiringViewModel = hiltViewModel()) {
    val firingTypes by vm.firingTypes.collectAsStateWithLifecycle()
    val query by vm.query.collectAsStateWithLifecycle()
    ItemListScreen("Pişirim Tipleri", firingTypes, query, vm::onQueryChange, { navController.navigate("detail/firing/${it.id}") }) { f ->
        Column(Modifier.padding(16.dp)) {
            Text(f.name, style = MaterialTheme.typography.titleMedium); Spacer(Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { InfoChip(f.firingCategory.displayName); InfoChip(f.atmosphere.displayName) }
            Spacer(Modifier.height(4.dp)); Text("${f.temperatureRangeCelsius} | ${f.coneRange}", style = MaterialTheme.typography.labelSmall)
            Text(f.description, style = MaterialTheme.typography.bodySmall, maxLines = 2)
        }
    }
}
