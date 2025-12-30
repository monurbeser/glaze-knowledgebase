package com.glaze.knowledgebase.domain.model

data class FiringType(
    val id: String, val name: String, val firingCategory: FiringCategory, val description: String,
    val temperatureRangeCelsius: String, val temperatureRangeFahrenheit: String, val coneRange: String,
    val atmosphere: FiringAtmosphere, val characteristics: List<String>, val historicalBackground: String,
    val commonCeramicTypes: List<String>, val relatedGlazeTypeIds: List<String>,
    val relatedFiringTypeIds: List<String>, val attribution: Attribution
)
enum class FiringCategory(val displayName: String) { LOW_FIRE("Düşük"), MID_FIRE("Orta"), HIGH_FIRE("Yüksek"), SPECIALTY("Özel") }
enum class FiringAtmosphere(val displayName: String) { OXIDATION("Oksidasyon"), REDUCTION("Redüksiyon"), NEUTRAL("Nötr"), VARIABLE("Değişken") }
