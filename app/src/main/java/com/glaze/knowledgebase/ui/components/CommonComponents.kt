package com.glaze.knowledgebase.ui.components
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.glaze.knowledgebase.domain.model.SafetyLevel
import com.glaze.knowledgebase.ui.theme.*

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(value = query, onValueChange = onQueryChange, modifier = modifier.fillMaxWidth(),
        placeholder = { Text("Ara…") }, leadingIcon = { Icon(Icons.Default.Search, null) },
        trailingIcon = { if (query.isNotEmpty()) IconButton(onClick = { onQueryChange("") }) { Icon(Icons.Default.Clear, "Temizle") } },
        singleLine = true, shape = MaterialTheme.shapes.large)
}

@Composable
fun <T> ItemListScreen(title: String, items: List<T>, searchQuery: String, onSearchQueryChange: (String) -> Unit,
    onItemClick: (T) -> Unit, itemContent: @Composable (T) -> Unit, isLoading: Boolean = false) {
    Column(Modifier.fillMaxSize()) {
        Text(title, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(16.dp))
        SearchBar(searchQuery, onSearchQueryChange, Modifier.padding(horizontal = 16.dp))
        Spacer(Modifier.height(8.dp))
        if (isLoading) Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        else if (items.isEmpty()) Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Sonuç bulunamadı") }
        else LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(items) { item -> Card(Modifier.fillMaxWidth().clickable { onItemClick(item) }) { itemContent(item) } }
        }
    }
}

@Composable
fun SafetyBadge(safetyLevel: SafetyLevel, modifier: Modifier = Modifier) {
    val (color, icon) = when (safetyLevel) {
        SafetyLevel.SAFE -> SafetySafe to Icons.Default.CheckCircle
        SafetyLevel.CAUTION -> SafetyCaution to Icons.Default.Warning
        SafetyLevel.IRRITANT -> SafetyCaution to Icons.Default.ReportProblem
        SafetyLevel.TOXIC -> SafetyToxic to Icons.Default.Dangerous
    }
    Surface(color = color.copy(alpha = 0.15f), shape = MaterialTheme.shapes.small, modifier = modifier) {
        Row(Modifier.padding(horizontal = 8.dp, vertical = 4.dp), horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = color, modifier = Modifier.size(16.dp))
            Text(safetyLevel.displayName, color = color, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
fun InfoChip(text: String, modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = MaterialTheme.shapes.small, modifier = modifier) {
        Text(text, Modifier.padding(horizontal = 8.dp, vertical = 4.dp), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSecondaryContainer)
    }
}

@Composable
fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, modifier = modifier.padding(vertical = 8.dp))
}

@Composable
fun AttributionCard(wikipediaTitle: String?, modifier: Modifier = Modifier) {
    if (wikipediaTitle != null) {
        Card(modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Row(Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Info, null, Modifier.size(20.dp))
                Column { Text("Kaynak", style = MaterialTheme.typography.labelSmall); Text(wikipediaTitle, style = MaterialTheme.typography.bodySmall) }
            }
        }
    }
}

@Composable
fun RelatedItemChip(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(onClick = onClick, color = MaterialTheme.colorScheme.primaryContainer, shape = MaterialTheme.shapes.small, modifier = modifier) {
        Text(text, Modifier.padding(horizontal = 12.dp, vertical = 6.dp), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
    }
}
