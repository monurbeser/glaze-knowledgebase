package com.glaze.knowledgebase.domain.model // Paket adını kendi yapına göre kontrol et

data class Recipe(
    val id: Int,
    val name: String,
    val cone: String,
    val atmosphere: String,
    val ingredients: List<Ingredient>,
    val additives: List<Ingredient> = emptyList(),
    val instructions: String = "",
    val image_name: String
)

data class Ingredient(
    val name: String,
    val amount: Double
)