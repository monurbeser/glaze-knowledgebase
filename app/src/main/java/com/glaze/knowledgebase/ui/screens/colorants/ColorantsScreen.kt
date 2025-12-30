// File: app/src/main/java/com/glaze/knowledgebase/ui/screens/colorants/ColorantsScreen.kt
package com.glaze.knowledgebase.ui.screens.colorants

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.glaze.knowledgebase.domain.model.Colorant
import com.glaze.knowledgebase.ui.components.InfoChip
import com.glaze.knowledgebase.ui.components.ItemListScreen
import com.glaze.knowledgebase.ui.components.SafetyBadge
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
class ColorantsViewModel @Inject constructor(
    private val repo: GlazeRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    @OptIn(FlowPreview::class)
    val colorants: StateFlow<List<Colorant>> =
        _query
            .debounce(300)
            .flatMapLatest { q ->
                if (q.isBlank()) repo.getAllColorants() else repo.searchColorants(q)
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
fun ColorantsScreen(
    navController: NavController,
    vm: ColorantsViewModel = hiltViewModel()
) {
    val colorants by vm.colorants.collectAsStateWithLifecycle()
    val query by vm.query.collectAsStateWithLifecycle()

    ItemListScreen(
        title = "Renk Vericiler",
        items = colorants,
        searchQuery = query,
        onSearchQueryChange = vm::onQueryChange,
        onItemClick = { item ->
            navController.navigate("detail/colorant/${item.id}")
        },
        itemContent = { c ->
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = c.name, style = MaterialTheme.typography.titleMedium)
                    SafetyBadge(c.safetyLevel)
                }

                Text(
                    text = c.chemicalName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))
                InfoChip(c.colorFamily.displayName)
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = c.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2
                )
            }
        }
    )
}
