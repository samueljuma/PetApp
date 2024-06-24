package com.samueljuma.petapp.views

import android.Manifest
import android.widget.Toast
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.samueljuma.petapp.data.Cat
import com.samueljuma.petapp.data.PermissionAction
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

    val context = LocalContext.current
    var showContent by rememberSaveable { mutableStateOf(false) }

    /**
     * We are only simulating  permission flow here since we do
     * not have a feature that uses location in the app
     */
    PermissionDialog(
        context = context,
        permission = Manifest.permission.ACCESS_COARSE_LOCATION
    ) { permissionAction ->
        when(permissionAction){
            is PermissionAction.PermissionDenied -> {
                showContent = false
            }
            is PermissionAction.PermissionGranted -> {
                showContent = true
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
        }

    }
    if(showContent){
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



}