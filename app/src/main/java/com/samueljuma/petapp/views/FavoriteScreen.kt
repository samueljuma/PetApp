package com.samueljuma.petapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.samueljuma.petapp.data.Cat
import com.samueljuma.petapp.viewmodel.PetsViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parameterArrayOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritePetsScreen(
    onPetClicked: (Cat) -> Unit
) {
    val petsViewModel: PetsViewModel = koinViewModel()
    // LaunchedEffect helps to execute the code only once and not on every recomposition
    LaunchedEffect(Unit) {
        petsViewModel.getFavoritePets()
    }

    val pets by petsViewModel.favoritePets.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Favorites") },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.padding( start = 10.dp, end = 10.dp),
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite Icon",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        content = { paddingValues ->
            if (pets.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "No Favorite Pets Yet")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(items = pets) { pet ->
                        PetListItem(
                            cat = pet,
                            onPetClicked = onPetClicked,
                            onFavoriteClicked = {
                                petsViewModel.updatePet(it)
                            }
                        )

                    }
                }


            }

        }
    )


}
