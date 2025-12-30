package com.glaze.knowledgebase.domain.model

data class Material(
    val id: String, val name: String, val alternativeNames: List<String>,
    val category: MaterialCategory, val description: String, val characteristics: List<String>,
    val commonUses: List<String>, val safetyLevel: SafetyLevel, val safetyNotes: String,
    val temperatureRange: String, val relatedMaterialIds: List<String>,
    val relatedColorantIds: List<String>, val relatedGlazeTypeIds: List<String>,
    val attribution: Attribution
)
enum class MaterialCategory(val displayName: String) {
    FLUX("Eritici"), GLASS_FORMER("Cam Oluşturucu"), STABILIZER("Dengeleyici"), OPACIFIER("Opaklaştırıcı")
}
enum class SafetyLevel(val displayName: String) {
    SAFE("Güvenli"), CAUTION("Dikkat"), IRRITANT("Tahriş Edici"), TOXIC("Toksik")
}
data class Attribution(val wikipediaTitle: String? = null, val wikipediaUrl: String? = null, val additionalSources: List<String> = emptyList())
