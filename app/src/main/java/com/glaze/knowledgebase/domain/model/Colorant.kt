package com.glaze.knowledgebase.domain.model

data class Colorant(
    val id: String, val name: String, val chemicalName: String, val colorFamily: ColorFamily,
    val description: String, val colorCharacteristics: List<String>,
    val atmosphereEffects: AtmosphereEffects, val temperatureSensitivity: String,
    val safetyLevel: SafetyLevel, val safetyNotes: String, val historicalBackground: String,
    val relatedColorantIds: List<String>, val relatedMaterialIds: List<String>,
    val relatedSurfaceEffectIds: List<String>, val attribution: Attribution
)
enum class ColorFamily(val displayName: String) {
    BLUE("Mavi"), GREEN("Yeşil"), RED("Kırmızı"), YELLOW("Sarı"), BROWN("Kahverengi"), BLACK("Siyah")
}
data class AtmosphereEffects(val oxidation: String, val reduction: String)
