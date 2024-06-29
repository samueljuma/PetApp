package com.samueljuma.petapp.views

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.samueljuma.petapp.navigation.Screens

@Composable
fun PetsNavigationRail(
    onFavoriteClicked: () -> Unit,
    onHomeClicked: () -> Unit,
    onDrawerClicked: () -> Unit,
) {
    val items = listOf(Screens.PetsScreen, Screens.FavouritesScreen)

    val selectedItem = remember { mutableStateOf(items[0]) }

    NavigationRail(
        modifier =
            Modifier
                .fillMaxHeight(),
    ) {
        NavigationRailItem(
            selected = false,
            onClick = onDrawerClicked,
            icon = {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu Icon",
                )
            },
        )

        NavigationRailItem(
            selected = selectedItem.value == Screens.PetsScreen,
            onClick = {
                onHomeClicked()
                selectedItem.value = Screens.PetsScreen
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home Icon",
                )
            },
        )

        NavigationRailItem(
            selected = selectedItem.value == Screens.FavouritesScreen,
            onClick = {
                onFavoriteClicked()
                selectedItem.value = Screens.FavouritesScreen
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorite Icon",
                )
            },
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PetsNavigationRailPreview() {
    PetsNavigationRail(
        onFavoriteClicked = {},
        onHomeClicked = {},
        onDrawerClicked = {},
    )
}