package com.samueljuma.petapp.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.samueljuma.petapp.views.PetDetailsScreen
import com.samueljuma.petapp.views.PetsScreen

@Composable
fun AppNavigation() {

    val ctx = LocalContext.current

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.PetsScreen.route
    ) {
        composable(Screens.PetsScreen.route) {
            PetsScreen(onPetClicked = { cat ->
                navController.navigate(Screens.PetDetailsScreen.route)
                Toast.makeText(ctx, "You clicked me ${cat.id}", Toast.LENGTH_SHORT).show()
            })
        }
        composable(Screens.PetDetailsScreen.route) {
            PetDetailsScreen(
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }

    }
}