package com.glaze.knowledgebase.domain.model

data class GlossaryTerm(
    val id: String, val term: String, val alternativeTerms: List<String>, val definition: String,
    val extendedDescription: String, val category: GlossaryCategory, val relatedTermIds: List<String>,
    val relatedMaterialIds: List<String>, val relatedColorantIds: List<String>,
    val relatedGlazeTypeIds: List<String>, val attribution: Attribution
)
enum class GlossaryCategory(val displayName: String) { MATERIAL("Malzeme"), TECHNIQUE("Teknik"), FIRING("Pişirim"), GENERAL("Genel") }
