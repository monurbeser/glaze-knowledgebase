package com.glaze.knowledgebase.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.glaze.knowledgebase.ui.screens.colorants.*
import com.glaze.knowledgebase.ui.screens.firing.*
import com.glaze.knowledgebase.ui.screens.glazetypes.*
import com.glaze.knowledgebase.ui.screens.glossary.*
import com.glaze.knowledgebase.ui.screens.home.*
import com.glaze.knowledgebase.ui.screens.materials.*
import com.glaze.knowledgebase.ui.screens.safety.*
import com.glaze.knowledgebase.ui.screens.settings.*
import com.glaze.knowledgebase.ui.screens.surfaceeffects.*
import com.glaze.knowledgebase.ui.screens.detail.*
import com.glaze.knowledgebase.ui.screens.recipes.RecipeDetailScreen
import com.glaze.knowledgebase.ui.screens.recipes.RecipeListScreen
import com.glaze.knowledgebase.ui.screens.recipes.RecipeViewModel

sealed class Screen(val route: String, val title: String, val icon: ImageVector, val selectedIcon: ImageVector) {
    data object Home : Screen("home", "Ana Sayfa", Icons.Outlined.Home, Icons.Filled.Home)
    data object Materials : Screen("materials", "Hammadde", Icons.Outlined.Science, Icons.Filled.Science)
    data object Recipes : Screen("recipes", "Reçeteler", Icons.Outlined.ReceiptLong, Icons.Filled.ReceiptLong)
    data object Colorants : Screen("colorants", "Renk", Icons.Outlined.Palette, Icons.Filled.Palette)
    data object GlazeTypes : Screen("glaze_types", "Sır", Icons.Outlined.AutoAwesome, Icons.Filled.AutoAwesome)
    data object Settings : Screen("settings", "Hakkında", Icons.Outlined.Info, Icons.Filled.Info)

    data object Firing : Screen("firing", "Pişirim", Icons.Outlined.LocalFireDepartment, Icons.Filled.LocalFireDepartment)
    data object SurfaceEffects : Screen("surface_effects", "Efekt", Icons.Outlined.Texture, Icons.Filled.Texture)
    data object Safety : Screen("safety", "Güvenlik", Icons.Outlined.HealthAndSafety, Icons.Filled.HealthAndSafety)
    data object Glossary : Screen("glossary", "Sözlük", Icons.Outlined.MenuBook, Icons.Filled.MenuBook)
}

val bottomNavItems = listOf(Screen.Home, Screen.Materials, Screen.Colorants, Screen.GlazeTypes, Screen.Settings)

@Composable
fun GlazeNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Detay sayfalarında alt barı gizle
    val showBottomBar = currentDestination?.route?.let {
        !it.contains("detail") && !it.contains("recipe_detail")
    } ?: true

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { screen ->
                        val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                        NavigationBarItem(
                            icon = { Icon(if (selected) screen.selectedIcon else screen.icon, null) },
                            label = { Text(text = screen.title, style = MaterialTheme.typography.labelSmall, maxLines = 1) },
                            selected = selected,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = screen.route != Screen.Home.route
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Materials.route) { MaterialsScreen(navController) }
            composable(Screen.Colorants.route) { ColorantsScreen(navController) }
            composable(Screen.GlazeTypes.route) { GlazeTypesScreen(navController) }
            composable(Screen.Firing.route) { FiringScreen(navController) }
            composable(Screen.SurfaceEffects.route) { SurfaceEffectsScreen(navController) }
            composable(Screen.Safety.route) { SafetyScreen(navController) }
            composable(Screen.Glossary.route) { GlossaryScreen(navController) }
            composable(Screen.Settings.route) { SettingsScreen() }

            // Reçete Listesi
            composable(Screen.Recipes.route) {
                RecipeListScreen(
                    onRecipeClick = { recipeId ->
                        navController.navigate("recipe_detail/$recipeId")
                    }
                )
            }

            // Reçete Detay (Fix: State takibi eklendi)
            composable(
                route = "recipe_detail/{recipeId}",
                arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
            ) { backStackEntry ->
                val recipeId = backStackEntry.arguments?.getInt("recipeId")
                val recipeViewModel: RecipeViewModel = hiltViewModel()
                val context = LocalContext.current

                // Verileri yükle
                LaunchedEffect(Unit) {
                    recipeViewModel.loadRecipes(context)
                }

                val recipes by recipeViewModel.recipes.collectAsState()
                val selectedRecipe = recipes.find { it.id == recipeId }

                if (selectedRecipe != null) {
                    RecipeDetailScreen(recipe = selectedRecipe)
                }
            }

            composable(
                "detail/{type}/{id}",
                arguments = listOf(
                    navArgument("type") { type = NavType.StringType },
                    navArgument("id") { type = NavType.StringType }
                )
            ) {
                DetailScreen(it.arguments?.getString("type") ?: "", it.arguments?.getString("id") ?: "", navController)
            }
        }
    }
}