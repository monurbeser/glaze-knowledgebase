// File: app/src/main/java/com/glaze/knowledgebase/ui/screens/glossary/GlossaryScreen.kt
package com.glaze.knowledgebase.ui.screens.glossary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.glaze.knowledgebase.data.repository.GlazeRepository
import com.glaze.knowledgebase.domain.model.GlossaryTerm
import com.glaze.knowledgebase.ui.components.InfoChip
import com.glaze.knowledgebase.ui.components.ItemListScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class GlossaryViewModel @Inject constructor(
    private val repo: GlazeRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    @OptIn(FlowPreview::class)
    val terms: StateFlow<List<GlossaryTerm>> =
        _query
            .debounce(300)
            .flatMapLatest { q ->
                if (q.isBlank()) repo.getAllGlossaryTerms()
                else repo.searchGlossaryTerms(q)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun onQueryChange(q: String) {
        _query.value = q
    }
}

@Composable
fun GlossaryScreen(
    navController: NavController,
    vm: GlossaryViewModel = hiltViewModel()
) {
    val terms by vm.terms.collectAsStateWithLifecycle()
    val query by vm.query.collectAsStateWithLifecycle()

    ItemListScreen(
        title = "Sözlük",
        items = terms,
        searchQuery = query,
        onSearchQueryChange = vm::onQueryChange,
        onItemClick = { item ->
            navController.navigate("detail/glossary/${item.id}")
        },
        itemContent = { t ->
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = t.term, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))

                InfoChip(t.category.displayName)
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = t.definition,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2
                )
            }
        }
    )
}
