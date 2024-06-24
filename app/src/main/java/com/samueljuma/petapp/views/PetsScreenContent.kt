package com.samueljuma.petapp.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.samueljuma.petapp.R
import com.samueljuma.petapp.data.Cat
import com.samueljuma.petapp.navigation.ContentType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetsScreenContent(
    modifier: Modifier,
    onPetClicked: (Cat) -> Unit,
    contentType: ContentType,
    petsUIState: PetsUIState,
    onFavoriteClicked: (Cat) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Petsy") },
                colors = if (contentType == ContentType.List) {
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    TopAppBarDefaults.topAppBarColors()
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                        painter = painterResource(id = R.drawable.petslogo),
                        contentDescription = "PetsLogo",
                        tint = if (contentType == ContentType.List) {MaterialTheme.colorScheme.onPrimary
                        }else{
                            MaterialTheme.colorScheme.primary
                        }
                    )
//                    Icon(
//                        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
//                        imageVector = Icons.Outlined.Face,
//                        contentDescription = "Pets",
//                        tint = MaterialTheme.colorScheme.onPrimary
//
//                    )
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = modifier
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    visible = petsUIState.isLoading
                ) {
                    CircularProgressIndicator()
                }


                AnimatedVisibility(
                    visible = petsUIState.pets.isNotEmpty()
                ) {
                    if (contentType == ContentType.List) {
                        PetList(
                            onPetClicked = onPetClicked,
                            pets = petsUIState.pets,
                            modifier = Modifier
                                .fillMaxWidth(),
                            onFavoriteClicked = onFavoriteClicked
                        )
                    } else {
                        PetListAndDetails(
                            pets = petsUIState.pets,
                            onFavoriteClicked = onFavoriteClicked
                        )
                    }

                }

                AnimatedVisibility(
                    visible = petsUIState.error != null
                ) {
                    Text(text = petsUIState.error ?: "Error")
                }
            }

        }
    )
}



