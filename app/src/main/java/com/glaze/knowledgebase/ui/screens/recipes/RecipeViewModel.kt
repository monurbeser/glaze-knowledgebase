package com.glaze.knowledgebase.ui.screens.recipes

import android.content.Context
import androidx.lifecycle.ViewModel
import com.glaze.knowledgebase.domain.model.Recipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor() : ViewModel() {

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    // JSON dosyasını okuyan fonksiyon
    fun loadRecipes(context: Context) {
        try {
            val jsonString = context.assets.open("seeds/recipes.json")
                .bufferedReader()
                .use { it.readText() }

            val listType = object : TypeToken<List<Recipe>>() {}.type
            val recipeList: List<Recipe> = Gson().fromJson(jsonString, listType)

            _recipes.value = recipeList
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // ID'ye göre tek bir reçete bulma (Detay sayfası için)
    fun getRecipeById(id: Int?): Recipe? {
        return _recipes.value.find { it.id == id }
    }
}