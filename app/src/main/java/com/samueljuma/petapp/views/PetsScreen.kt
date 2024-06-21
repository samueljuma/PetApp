package com.samueljuma.petapp.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.samueljuma.petapp.data.Cat
import com.samueljuma.petapp.navigation.ContentType
import com.samueljuma.petapp.viewmodel.PetsViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetsScreen(
    onPetClicked: (Cat) -> Unit,
    contentType: ContentType
){
    val petsViewModel: PetsViewModel = koinViewModel()
    val petsUIState by petsViewModel.petsUIState.collectAsStateWithLifecycle()

    PetsScreenContent(
        modifier = Modifier.fillMaxSize() ,
        onPetClicked = onPetClicked,
        contentType = contentType,
        petsUIState = petsUIState,
        onFavoriteClicked = { cat->
            petsViewModel.updatePet(cat)
        }
    )

}