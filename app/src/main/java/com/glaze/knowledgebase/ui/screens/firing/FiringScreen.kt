// File: app/src/main/java/com/glaze/knowledgebase/ui/screens/firing/FiringScreen.kt
package com.glaze.knowledgebase.ui.screens.firing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.glaze.knowledgebase.domain.model.FiringType
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
class FiringViewModel @Inject constructor(
    private val repo: GlazeRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    @OptIn(FlowPreview::class)
    val firingTypes: StateFlow<List<FiringType>> =
        _query
            .debounce(300)
            .flatMapLatest { q ->
                if (q.isBlank()) repo.getAllFiringTypes() else repo.searchFiringTypes(q)
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
fun FiringScreen(
    navController: NavController,
    vm: FiringViewModel = hiltViewModel()
) {
    val firingTypes by vm.firingTypes.collectAsStateWithLifecycle()
    val query by vm.query.collectAsStateWithLifecycle()

    ItemListScreen(
        title = "Pişirim Tipleri",
        items = firingTypes,
        searchQuery = query,
        onSearchQueryChange = vm::onQueryChange,
        onItemClick = { item ->
            navController.navigate("detail/firing/${item.id}")
        },
        itemContent = { f ->
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = f.name, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    InfoChip(f.firingCategory.displayName)
                    InfoChip(f.atmosphere.displayName)
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${f.temperatureRangeCelsius} | ${f.coneRange}",
                    style = MaterialTheme.typography.labelSmall
                )

                Text(
                    text = f.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2
                )
            }
        }
    )
}
