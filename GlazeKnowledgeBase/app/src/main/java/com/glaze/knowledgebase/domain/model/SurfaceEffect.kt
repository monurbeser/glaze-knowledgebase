package com.glaze.knowledgebase.domain.model

data class SurfaceEffect(
    val id: String, val name: String, val effectType: EffectType, val description: String,
    val visualAppearance: String, val causes: List<String>, val associatedFactors: List<String>,
    val historicalContext: String, val notableExamples: List<String>, val relatedEffectIds: List<String>,
    val relatedGlazeTypeIds: List<String>, val relatedColorantIds: List<String>, val attribution: Attribution
)
enum class EffectType(val displayName: String) { DECORATIVE("Dekoratif"), TEXTURAL("Dokusal"), COLOR_VARIATION("Renk"), CRYSTALLINE("Kristal") }
