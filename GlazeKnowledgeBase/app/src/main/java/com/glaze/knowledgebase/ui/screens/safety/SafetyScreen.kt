package com.glaze.knowledgebase.ui.screens.safety
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
import com.glaze.knowledgebase.domain.model.SafetyInfo
import com.glaze.knowledgebase.ui.components.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SafetyViewModel @Inject constructor(private val repo: GlazeRepository) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()
    @OptIn(FlowPreview::class)
    val safetyInfo: StateFlow<List<SafetyInfo>> = _query.debounce(300).flatMapLatest { q -> if (q.isBlank()) repo.getAllSafetyInfo() else repo.searchSafetyInfo(q) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    fun onQueryChange(q: String) { _query.value = q }
}

@Composable
fun SafetyScreen(navController: NavController, vm: SafetyViewModel = hiltViewModel()) {
    val info by vm.safetyInfo.collectAsStateWithLifecycle()
    val query by vm.query.collectAsStateWithLifecycle()
    ItemListScreen("Güvenlik Bilgileri", info, query, vm::onQueryChange, { navController.navigate("detail/safety/${it.id}") }) { s ->
        Column(Modifier.padding(16.dp)) {
            Text(s.title, style = MaterialTheme.typography.titleMedium); Spacer(Modifier.height(4.dp))
            InfoChip(s.category.displayName); Spacer(Modifier.height(4.dp))
            Text(s.description, style = MaterialTheme.typography.bodySmall, maxLines = 2)
        }
    }
}
