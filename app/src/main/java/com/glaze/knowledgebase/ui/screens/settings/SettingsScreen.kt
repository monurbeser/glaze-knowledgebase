package com.glaze.knowledgebase.ui.screens.settings
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.glaze.knowledgebase.data.repository.SettingsRepository
import com.glaze.knowledgebase.domain.model.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repo: SettingsRepository) : ViewModel() {
    val themeMode = repo.themeMode.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ThemeMode.SYSTEM)
    fun setThemeMode(mode: ThemeMode) { viewModelScope.launch { repo.setThemeMode(mode) } }
}

@Composable
fun SettingsScreen(vm: SettingsViewModel = hiltViewModel()) {
    val themeMode by vm.themeMode.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Ayarlar", style = MaterialTheme.typography.headlineMedium); Spacer(Modifier.height(24.dp))
        Card(Modifier.fillMaxWidth()) {
            ListItem(headlineContent = { Text("Tema") },
                supportingContent = { Text(when (themeMode) { ThemeMode.SYSTEM -> "Sistem"; ThemeMode.LIGHT -> "Açık"; ThemeMode.DARK -> "Koyu" }) },
                leadingContent = { Icon(Icons.Default.Palette, null) }, modifier = Modifier.clickable { showDialog = true })
        }
        Spacer(Modifier.height(16.dp))
        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Hakkında", style = MaterialTheme.typography.titleMedium); Spacer(Modifier.height(8.dp))
                Text("Seramik Bilgi Bankası.", style = MaterialTheme.typography.bodyMedium)
                Text("Formül veya reçete içermez.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Sürüm 1.0.0", style = MaterialTheme.typography.labelSmall)
                Text("Pia Ceramic", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
    if (showDialog) {
        AlertDialog(onDismissRequest = { showDialog = false }, title = { Text("Tema") }, text = {
            Column { ThemeMode.entries.forEach { mode ->
                Row(Modifier.fillMaxWidth().clickable { vm.setThemeMode(mode); showDialog = false }.padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = themeMode == mode, onClick = null); Spacer(Modifier.width(8.dp))
                    Text(when (mode) { ThemeMode.SYSTEM -> "Sistem"; ThemeMode.LIGHT -> "Açık"; ThemeMode.DARK -> "Koyu" })
                }
            }}
        }, confirmButton = { TextButton(onClick = { showDialog = false }) { Text("İptal") } })
    }
}
