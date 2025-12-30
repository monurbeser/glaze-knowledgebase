package com.glaze.knowledgebase.domain.model

data class SafetyInfo(
    val id: String, val title: String, val category: SafetyCategory, val description: String,
    val hazardTypes: List<HazardType>, val protectiveMeasures: List<String>, val symptoms: List<String>,
    val firstAidInfo: String, val storageGuidelines: String, val disposalGuidelines: String,
    val regulatoryInfo: String, val relatedMaterialIds: List<String>, val relatedColorantIds: List<String>, val attribution: Attribution
)
enum class SafetyCategory(val displayName: String) { MATERIAL_SAFETY("Malzeme"), STUDIO_SAFETY("Stüdyo"), RESPIRATORY("Solunum") }
enum class HazardType(val displayName: String) { INHALATION("Soluma"), SKIN_CONTACT("Cilt"), INGESTION("Yutma") }
