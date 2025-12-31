package com.glaze.knowledgebase.ui.screens.recipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.glaze.knowledgebase.Recipe
import com.glaze.knowledgebase.Ingredient
import com.glaze.knowledgebase.R

@Composable
fun RecipeDetailScreen(recipe: Recipe) {
    val context = LocalContext.current
    val imageResId = remember(recipe.image_name) {
        context.resources.getIdentifier(recipe.image_name, "drawable", context.packageName)
    }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().height(250.dp)
            ) {
                Image(
                    painter = painterResource(id = if (imageResId != 0) imageResId else R.drawable.ic_launcher_background),
                    contentDescription = recipe.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text(text = recipe.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                InfoBadge(text = recipe.cone, color = Color(0xFF8B5A2B))
                Spacer(modifier = Modifier.width(8.dp))
                InfoBadge(text = recipe.atmosphere, color = Color(0xFF4A6572))
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
        }

        item {
            Text("Ana Reçete (Base)", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
            recipe.ingredients.forEach { ingredient ->
                IngredientRow(ingredient)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (recipe.additives.isNotEmpty()) {
            item {
                Text("Eklemeler (Additives)", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
                recipe.additives.forEach { additive ->
                    IngredientRow(additive, isAdditive = true)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        if (recipe.instructions.isNotEmpty()) {
            item {
                Text("Notlar ve Talimatlar", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
                Text(
                    text = recipe.instructions,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun IngredientRow(ingredient: Ingredient, isAdditive: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = ingredient.name, style = MaterialTheme.typography.bodyLarge)
        Text(
            text = "${ingredient.amount}",
            fontWeight = FontWeight.Bold,
            color = if (isAdditive) Color(0xFFD32F2F) else Color.Black
        )
    }
}

@Composable
fun InfoBadge(text: String, color: Color) {
    Surface(color = color, shape = RoundedCornerShape(8.dp)) {
        Text(
            text = text,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}