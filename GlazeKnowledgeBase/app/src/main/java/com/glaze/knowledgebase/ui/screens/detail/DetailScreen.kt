package com.glaze.knowledgebase.ui.screens.detail
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.glaze.knowledgebase.data.repository.GlazeRepository
import com.glaze.knowledgebase.domain.model.*
import com.glaze.knowledgebase.ui.components.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

sealed class DetailItem {
    data class Mat(val d: Material) : DetailItem()
    data class Col(val d: Colorant) : DetailItem()
    data class Glz(val d: GlazeType) : DetailItem()
    data class Fir(val d: FiringType) : DetailItem()
    data class Eff(val d: SurfaceEffect) : DetailItem()
    data class Saf(val d: SafetyInfo) : DetailItem()
    data class Gls(val d: GlossaryTerm) : DetailItem()
}

@HiltViewModel
class DetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle, private val repo: GlazeRepository) : ViewModel() {
    private val type: String = savedStateHandle["type"] ?: ""
    private val id: String = savedStateHandle["id"] ?: ""
    val item: StateFlow<DetailItem?> = when (type) {
        "material" -> repo.getMaterialById(id).map { it?.let { DetailItem.Mat(it) } }
        "colorant" -> repo.getColorantById(id).map { it?.let { DetailItem.Col(it) } }
        "glazetype" -> repo.getGlazeTypeById(id).map { it?.let { DetailItem.Glz(it) } }
        "firing" -> repo.getFiringTypeById(id).map { it?.let { DetailItem.Fir(it) } }
        "surfaceeffect" -> repo.getSurfaceEffectById(id).map { it?.let { DetailItem.Eff(it) } }
        "safety" -> repo.getSafetyInfoById(id).map { it?.let { DetailItem.Saf(it) } }
        "glossary" -> repo.getGlossaryTermById(id).map { it?.let { DetailItem.Gls(it) } }
        else -> flowOf(null)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    fun getMaterials(ids: List<String>) = repo.getMaterialsByIds(ids)
    fun getColorants(ids: List<String>) = repo.getColorantsByIds(ids)
    fun getGlazeTypes(ids: List<String>) = repo.getGlazeTypesByIds(ids)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(type: String, id: String, navController: NavController, vm: DetailViewModel = hiltViewModel()) {
    val item by vm.item.collectAsStateWithLifecycle()
    Scaffold(topBar = { TopAppBar(title = { Text(item?.let { getTitle(it) } ?: "…") }, navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Geri") } }) }) { p ->
        item?.let {
            Column(Modifier.fillMaxSize().padding(p).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                when (it) {
                    is DetailItem.Mat -> MatContent(it.d, navController, vm)
                    is DetailItem.Col -> ColContent(it.d)
                    is DetailItem.Glz -> GlzContent(it.d)
                    is DetailItem.Fir -> FirContent(it.d)
                    is DetailItem.Eff -> EffContent(it.d)
                    is DetailItem.Saf -> SafContent(it.d)
                    is DetailItem.Gls -> GlsContent(it.d)
                }
            }
        }
    }
}

private fun getTitle(i: DetailItem) = when (i) { is DetailItem.Mat -> i.d.name; is DetailItem.Col -> i.d.name; is DetailItem.Glz -> i.d.name; is DetailItem.Fir -> i.d.name; is DetailItem.Eff -> i.d.name; is DetailItem.Saf -> i.d.title; is DetailItem.Gls -> i.d.term }

@Composable private fun MatContent(m: Material, nav: NavController, vm: DetailViewModel) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { InfoChip(m.category.displayName); SafetyBadge(m.safetyLevel) }
    Text(m.description, style = MaterialTheme.typography.bodyLarge)
    if (m.characteristics.isNotEmpty()) { SectionTitle("Özellikler"); m.characteristics.forEach { Text("• $it") } }
    if (m.commonUses.isNotEmpty()) { SectionTitle("Kullanım"); m.commonUses.forEach { Text("• $it") } }
    SectionTitle("Sıcaklık"); Text(m.temperatureRange)
    SectionTitle("Güvenlik"); Text(m.safetyNotes)
    RelatedSection("İlgili Hammaddeler", m.relatedMaterialIds, "material", nav, vm)
    AttributionCard(m.attribution.wikipediaTitle)
}

@Composable private fun ColContent(c: Colorant) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { InfoChip(c.colorFamily.displayName); SafetyBadge(c.safetyLevel) }
    Text(c.chemicalName, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Text(c.description, style = MaterialTheme.typography.bodyLarge)
    SectionTitle("Atmosfer"); Text("Oksidasyon: ${c.atmosphereEffects.oxidation}"); Text("Redüksiyon: ${c.atmosphereEffects.reduction}")
    SectionTitle("Tarih"); Text(c.historicalBackground)
    SectionTitle("Güvenlik"); Text(c.safetyNotes)
    AttributionCard(c.attribution.wikipediaTitle)
}

@Composable private fun GlzContent(g: GlazeType) {
    InfoChip(g.category.displayName); Text(g.description, style = MaterialTheme.typography.bodyLarge)
    SectionTitle("Yüzey"); Text(g.surfaceQuality)
    SectionTitle("Işık"); Text(g.lightInteraction)
    SectionTitle("Sıcaklık"); Text(g.typicalTemperatureRange)
    SectionTitle("Tarih"); Text(g.historicalContext)
    AttributionCard(g.attribution.wikipediaTitle)
}

@Composable private fun FirContent(f: FiringType) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { InfoChip(f.firingCategory.displayName); InfoChip(f.atmosphere.displayName) }
    Text(f.description, style = MaterialTheme.typography.bodyLarge)
    Card(Modifier.fillMaxWidth()) { Column(Modifier.padding(16.dp)) { Text("${f.temperatureRangeCelsius} | ${f.temperatureRangeFahrenheit}"); Text("Koni: ${f.coneRange}") } }
    SectionTitle("Tarih"); Text(f.historicalBackground)
    AttributionCard(f.attribution.wikipediaTitle)
}

@Composable private fun EffContent(e: SurfaceEffect) {
    InfoChip(e.effectType.displayName); Text(e.description, style = MaterialTheme.typography.bodyLarge)
    SectionTitle("Görünüm"); Text(e.visualAppearance)
    if (e.causes.isNotEmpty()) { SectionTitle("Nedenler"); e.causes.forEach { Text("• $it") } }
    SectionTitle("Tarih"); Text(e.historicalContext)
    AttributionCard(e.attribution.wikipediaTitle)
}

@Composable private fun SafContent(s: SafetyInfo) {
    InfoChip(s.category.displayName); Text(s.description, style = MaterialTheme.typography.bodyLarge)
    if (s.protectiveMeasures.isNotEmpty()) { SectionTitle("Koruma"); s.protectiveMeasures.forEach { Text("• $it") } }
    if (s.symptoms.isNotEmpty()) { SectionTitle("Belirtiler"); s.symptoms.forEach { Text("• $it") } }
    SectionTitle("İlk Yardım"); Text(s.firstAidInfo)
    AttributionCard(s.attribution.wikipediaTitle)
}

@Composable private fun GlsContent(g: GlossaryTerm) {
    InfoChip(g.category.displayName)
    if (g.alternativeTerms.isNotEmpty()) Text("Diğer: ${g.alternativeTerms.joinToString()}", style = MaterialTheme.typography.bodySmall)
    Text(g.definition, style = MaterialTheme.typography.titleMedium)
    Text(g.extendedDescription, style = MaterialTheme.typography.bodyLarge)
    AttributionCard(g.attribution.wikipediaTitle)
}

@Composable private fun RelatedSection(title: String, ids: List<String>, type: String, nav: NavController, vm: DetailViewModel) {
    if (ids.isEmpty()) return
    val items by vm.getMaterials(ids).collectAsStateWithLifecycle(emptyList())
    if (items.isNotEmpty()) {
        SectionTitle(title)
        Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items.forEach { RelatedItemChip(it.name, { nav.navigate("detail/$type/${it.id}") }) }
        }
    }
}
