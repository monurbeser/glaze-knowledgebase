package com.glaze.knowledgebase.ui.screens.colorants
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
import com.glaze.knowledgebase.domain.model.Colorant
import com.glaze.knowledgebase.ui.components.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ColorantsViewModel @Inject constructor(private val repo: GlazeRepository) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()
    @OptIn(FlowPreview::class)
    val colorants: StateFlow<List<Colorant>> = _query.debounce(300).flatMapLatest { q -> if (q.isBlank()) repo.getAllColorants() else repo.searchColorants(q) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    fun onQueryChange(q: String) { _query.value = q }
}

@Composable
fun ColorantsScreen(navController: NavController, vm: ColorantsViewModel = hiltViewModel()) {
    val colorants by vm.colorants.collectAsStateWithLifecycle()
    val query by vm.query.collectAsStateWithLifecycle()
    ItemListScreen("Renk Vericiler", colorants, query, vm::onQueryChange, { navController.navigate("detail/colorant/${it.id}") }) { c ->
        Column(Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) { Text(c.name, style = MaterialTheme.typography.titleMedium); SafetyBadge(c.safetyLevel) }
            Text(c.chemicalName, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(4.dp)); InfoChip(c.colorFamily.displayName); Spacer(Modifier.height(4.dp))
            Text(c.description, style = MaterialTheme.typography.bodySmall, maxLines = 2)
        }
    }
}
