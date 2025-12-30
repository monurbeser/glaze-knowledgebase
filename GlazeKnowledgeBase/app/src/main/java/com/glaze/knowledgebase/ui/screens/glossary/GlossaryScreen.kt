package com.glaze.knowledgebase.ui.screens.glossary
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
import com.glaze.knowledgebase.domain.model.GlossaryTerm
import com.glaze.knowledgebase.ui.components.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class GlossaryViewModel @Inject constructor(private val repo: GlazeRepository) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()
    @OptIn(FlowPreview::class)
    val terms: StateFlow<List<GlossaryTerm>> = _query.debounce(300).flatMapLatest { q -> if (q.isBlank()) repo.getAllGlossaryTerms() else repo.searchGlossaryTerms(q) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    fun onQueryChange(q: String) { _query.value = q }
}

@Composable
fun GlossaryScreen(navController: NavController, vm: GlossaryViewModel = hiltViewModel()) {
    val terms by vm.terms.collectAsStateWithLifecycle()
    val query by vm.query.collectAsStateWithLifecycle()
    ItemListScreen("Sözlük", terms, query, vm::onQueryChange, { navController.navigate("detail/glossary/${it.id}") }) { t ->
        Column(Modifier.padding(16.dp)) {
            Text(t.term, style = MaterialTheme.typography.titleMedium); Spacer(Modifier.height(4.dp))
            InfoChip(t.category.displayName); Spacer(Modifier.height(4.dp))
            Text(t.definition, style = MaterialTheme.typography.bodySmall, maxLines = 2)
        }
    }
}
