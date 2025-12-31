@Composable
fun RecipeDetailScreen(recipe: Recipe) {
    val context = LocalContext.current

    // Görseli drawable içinden isme göre buluyoruz
    val imageResId = remember(recipe.image_name) {
        context.resources.getIdentifier(recipe.image_name, "drawable", context.packageName)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 1. Sır Görseli (Pişmiş Çıktı)
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Image(
                    painter = painterResource(id = if (imageResId != 0) imageResId else R.drawable.placeholder_glaze),
                    contentDescription = recipe.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // 2. Başlık ve Bilgi Kartları (Cone & Atmosphere)
        item {
            Text(text = recipe.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                InfoBadge(text = recipe.cone, color = Color(0xFF8B5A2B))
                Spacer(modifier = Modifier.width(8.dp))
                InfoBadge(text = recipe.atmosphere, color = Color(0xFF4A6572))
            }
            Divider(modifier = Modifier.padding(vertical = 12.dp))
        }

        // 3. Ana Reçete (Ingredients)
        item {
            Text("Ana Reçete (Base)", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
            recipe.ingredients.forEach { ingredient ->
                IngredientRow(ingredient)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // 4. Eklemeler (Additives/Colorants)
        if (recipe.additives.isNotEmpty()) {
            item {
                Text("Eklemeler (Additives)", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
                recipe.additives.forEach { additive ->
                    IngredientRow(additive, isAdditive = true)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // 5. Talimatlar
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
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