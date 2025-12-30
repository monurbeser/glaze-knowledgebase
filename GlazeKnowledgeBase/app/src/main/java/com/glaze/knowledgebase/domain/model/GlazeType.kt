package com.glaze.knowledgebase.domain.model

data class GlazeType(
    val id: String, val name: String, val category: GlazeCategory, val description: String,
    val visualCharacteristics: List<String>, val surfaceQuality: String, val lightInteraction: String,
    val typicalTemperatureRange: String, val historicalContext: String,
    val commonApplications: List<String>, val relatedGlazeTypeIds: List<String>,
    val relatedSurfaceEffectIds: List<String>, val relatedFiringTypeIds: List<String>, val attribution: Attribution
)
enum class GlazeCategory(val displayName: String) {
    MATTE("Mat"), GLOSSY("Parlak"), SATIN("Saten"), CRYSTALLINE("Kristal"), CELADON("Seladon"), TEMMOKU("Temmoku")
}
