package com.glaze.knowledgebase.ui.screens.home
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.glaze.knowledgebase.data.seed.DatabaseSeeder
import com.glaze.knowledgebase.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val seeder: DatabaseSeeder) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()
    init { viewModelScope.launch { seeder.seedIfEmpty(); _isLoading.value = false } }
}

data class MenuItem(val title: String, val subtitle: String, val icon: ImageVector, val route: String)

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val menuItems = listOf(
        MenuItem("Hammaddeler", "Eriticiler, cam oluşturucular", Icons.Default.Science, Screen.Materials.route),
        MenuItem("Renk Vericiler", "Oksitler ve pigmentler", Icons.Default.Palette, Screen.Colorants.route),
        MenuItem("Sır Tipleri", "Mat, parlak, saten", Icons.Default.AutoAwesome, Screen.GlazeTypes.route),
        MenuItem("Pişirim", "Sıcaklık ve atmosfer", Icons.Default.LocalFireDepartment, Screen.Firing.route),
        MenuItem("Yüzey Efektleri", "Kristal, crawling", Icons.Default.Texture, Screen.SurfaceEffects.route),
        MenuItem("Güvenlik", "Malzeme güvenliği", Icons.Default.HealthAndSafety, Screen.Safety.route),
        MenuItem("Sözlük", "Seramik terimleri", Icons.Default.MenuBook, Screen.Glossary.route) ,
                MenuItem("Hakkında", "Uygulama bilgileri", Icons.Default.Info, Screen.Settings.route)

    )
    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) { CircularProgressIndicator(); Spacer(Modifier.height(16.dp)); Text("Hazırlanıyor…") }
        }
    } else {
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Text("Glaze Knowledge Base", style = MaterialTheme.typography.headlineLarge)
            Text("Seramik referans uygulaması", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(24.dp))
            LazyVerticalGrid(columns = GridCells.Fixed(2), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(menuItems) { item ->
                    Card(Modifier.fillMaxWidth().aspectRatio(1.1f).clickable { navController.navigate(item.route) }) {
                        Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(item.icon, null, Modifier.size(40.dp), tint = MaterialTheme.colorScheme.primary)
                            Spacer(Modifier.height(8.dp))
                            Text(item.title, style = MaterialTheme.typography.titleSmall, textAlign = TextAlign.Center)
                            Text(item.subtitle, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
    }
}
