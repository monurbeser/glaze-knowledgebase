// File: app/src/main/java/com/glaze/knowledgebase/ui/screens/materials/MaterialsScreen.kt
package com.glaze.knowledgebase.ui.screens.materials

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
import com.glaze.knowledgebase.domain.model.Material
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
class MaterialsViewModel @Inject constructor(
    private val repo: GlazeRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    @OptIn(FlowPreview::class)
    val materials: StateFlow<List<Material>> =
        _query
            .debounce(300)
            .flatMapLatest { q ->
                if (q.isBlank()) repo.getAllMaterials()
                else repo.searchMaterials(q)
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
fun MaterialsScreen(
    navController: NavController,
    vm: MaterialsViewModel = hiltViewModel()
) {
    val materials by vm.materials.collectAsStateWithLifecycle()
    val query by vm.query.collectAsStateWithLifecycle()

    ItemListScreen(
        title = "Hammaddeler",
        items = materials,
        searchQuery = query,
        onSearchQueryChange = vm::onQueryChange,
        onItemClick = { item ->
            navController.navigate("detail/material/${item.id}")
        },
        itemContent = { m ->
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = m.name, style = MaterialTheme.typography.titleMedium)
                    SafetyBadge(m.safetyLevel)
                }

                Spacer(modifier = Modifier.height(4.dp))
                InfoChip(m.category.displayName)
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = m.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2
                )
            }
        }
    )
}
