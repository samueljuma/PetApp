package com.samueljuma.petapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.samueljuma.petapp.data.Cat
import com.samueljuma.petapp.viewmodel.PetsViewModel
import org.koin.androidx.compose.koinViewModel

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

    if(pets.isEmpty()){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "No Favorite Pets Yet")
        }
    }else{
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(items = pets){ pet ->
                PetListItem(
                    cat = pet ,
                    onPetClicked = onPetClicked,
                    onFavoriteClicked = {
                        petsViewModel.updatePet(it)
                    }
                )

            }
        }
    }


}
