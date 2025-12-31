package com.glaze.knowledgebase.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp // Hatanın çözümü için eklenen kritik satır
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
        MenuItem("Sır Reçeteleri", "Reçeteler ve hesaplayıcı", Icons.Default.ReceiptLong, Screen.Recipes.route),
        MenuItem("Sır Tipleri", "Mat, parlak, saten", Icons.Default.AutoAwesome, Screen.GlazeTypes.route),
        MenuItem("Pişirim", "Sıcaklık ve atmosfer", Icons.Default.LocalFireDepartment, Screen.Firing.route),
        MenuItem("Yüzey Efektleri", "Kristal, crawling", Icons.Default.Texture, Screen.SurfaceEffects.route),
        MenuItem("Güvenlik", "Malzeme güvenliği", Icons.Default.HealthAndSafety, Screen.Safety.route),
        MenuItem("Sözlük", "Seramik terimleri", Icons.Default.MenuBook, Screen.Glossary.route),
        MenuItem("Hakkında", "Uygulama bilgileri", Icons.Default.Info, Screen.Settings.route)
    )

    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                Spacer(Modifier.height(16.dp))
                Text("Hazırlanıyor…")
            }
        }
    } else {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Başlık Bölümü
            Text(
                text = "Seramik Bilgi Bankası",
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = "Hammaddeler, Sır Türleri, Renk Vericiler, Pişirim Türleri Hakkında Pratik Bilgiler",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(24.dp))

            // Orta Bölüm: Kartlar
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(menuItems) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.1f)
                            .clickable { navController.navigate(item.route) },
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                    ) {
                        Column(
                            Modifier.fillMaxSize().padding(12.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null,
                                modifier = Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = item.subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Alt Bölüm: Daraltılmış Sorumluluk Beyanı
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Sorumluluk Beyanı: Bu uygulama bilgilendirme amaçlıdır. Kimyasal kullanımında yerel güvenlik talimatlarına uyunuz. Geliştirici, bu bilgilerin kullanımından doğabilecek sonuçlardan sorumlu tutulamaz.",
                    // .sp birimi artık yukarıdaki import sayesinde tanınacak
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, lineHeight = 12.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.75f) // Yazıyı ekranın ortasında dar bir şerit yapar
                        .padding(top = 16.dp, bottom = 4.dp)
                )
            }
        }
    }
}