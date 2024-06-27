package com.samueljuma.petapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.samueljuma.petapp.navigation.AppNavigation
import com.samueljuma.petapp.navigation.AppNavigationContent
import com.samueljuma.petapp.navigation.ContentType
import com.samueljuma.petapp.navigation.DeviceFoldPosture
import com.samueljuma.petapp.navigation.NavigationType
import com.samueljuma.petapp.navigation.Screens
import com.samueljuma.petapp.navigation.isBookPosture
import com.samueljuma.petapp.navigation.isSeparatingPosture
import com.samueljuma.petapp.ui.theme.PetAppTheme
import com.samueljuma.petapp.views.PetList
import com.samueljuma.petapp.views.PetsNavigationDrawer
import com.samueljuma.petapp.workers.PetsSyncWorker
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import leakcanary.AppWatcher

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        startPetsSync()

        val deviceFoldingPostureFlow = WindowInfoTracker.getOrCreate(this).windowLayoutInfo(this)
            .flowWithLifecycle(this.lifecycle)
            .map { layoutInfo->
                val foldingFeature = layoutInfo.displayFeatures
                    .filterIsInstance<FoldingFeature>()
                    .firstOrNull()
                when{
                    isBookPosture(foldingFeature) -> DeviceFoldPosture.BookPosture(foldingFeature.bounds)
                    isSeparatingPosture(foldingFeature) -> {
                        DeviceFoldPosture.SeparatingPosture(foldingFeature.bounds, foldingFeature.orientation)
                    }
                    else -> {
                        DeviceFoldPosture.NormalPosture
                    }
                }

            }.stateIn(
                scope = lifecycleScope,
                started = SharingStarted.Eagerly,
                initialValue = DeviceFoldPosture.NormalPosture
            )

        setContent {
            val devicePosture = deviceFoldingPostureFlow.collectAsStateWithLifecycle().value
            val windowSizeClass = calculateWindowSizeClass(activity = this)
            val scope = rememberCoroutineScope()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val navController = rememberNavController()
            PetAppTheme {

                val navigationType: NavigationType
                val contentType: ContentType
                when(windowSizeClass.widthSizeClass){
                    WindowWidthSizeClass.Compact ->{
                        navigationType = NavigationType.BottomNavigation
                        contentType = ContentType.List
                    }
                    WindowWidthSizeClass.Medium ->{
                        navigationType = NavigationType.NavigationRail
                        contentType = if(devicePosture is DeviceFoldPosture.BookPosture || devicePosture is DeviceFoldPosture.SeparatingPosture){
                            ContentType.ListAndDetail
                        }else{
                            ContentType.List
                        }
                    }
                    WindowWidthSizeClass.Expanded ->{
                        navigationType = if(devicePosture is DeviceFoldPosture.BookPosture){
                            NavigationType.NavigationRail
                        }else{
                            NavigationType.NavigationDrawer
                        }
                        contentType = ContentType.ListAndDetail
                    }
                    else ->{
                        navigationType = NavigationType.BottomNavigation
                        contentType = ContentType.List
                    }
                }
                if(navigationType == NavigationType.NavigationDrawer){
                    PermanentNavigationDrawer(
                        drawerContent = {
                            PermanentDrawerSheet {
                                PetsNavigationDrawer(
                                    onFavoriteClicked = {
                                        navController.navigate(Screens.FavouritesScreen.route)
                                    },
                                    onHomeClicked = {
                                        navController.navigate(Screens.PetsScreen.route)
                                    }
                                )
                            }
                        }
                    ) {
                        AppNavigationContent(
                            contentType = contentType,
                            navigationType = navigationType,
                            onFavoriteClicked = {
                                navController.navigate(Screens.FavouritesScreen.route)
                            },
                            onHomeClicked = {
                                navController.navigate(Screens.PetsScreen.route)
                            },
                            navHostController = navController
                        ) {

                        }
                    }
                }else{
                    ModalNavigationDrawer(
                        drawerContent = {
                            ModalDrawerSheet {
                                PetsNavigationDrawer(
                                    onFavoriteClicked = {
                                        navController.navigate(Screens.FavouritesScreen.route)
                                    },
                                    onHomeClicked = {
                                        navController.navigate(Screens.PetsScreen.route)
                                    },
                                    onDrawerClicked = {
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    }
                                )
                            }
                        },
                        drawerState = drawerState
                    ) {
                        AppNavigationContent(
                            contentType = contentType,
                            navigationType = navigationType,
                            onFavoriteClicked = {
                                navController.navigate(Screens.FavouritesScreen.route)
                            },
                            onHomeClicked = {
                                navController.navigate(Screens.PetsScreen.route)
                            },
                            navHostController = navController,
                            onDrawerClicked = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        )

                    }
                }
            }

        }

        AppWatcher.objectWatcher.expectWeaklyReachable(
            LeakTestUtils.leakCanaryTest,
            "Static reference to LeakCanaryTest"
        )
    }

    private fun startPetsSync(){
        val syncPetsWorkRequest = OneTimeWorkRequestBuilder<PetsSyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(true)
                    .build()
            ).build()
        WorkManager.getInstance(applicationContext).enqueueUniqueWork(
            "PetsSyncWorker",
            ExistingWorkPolicy.KEEP,
            syncPetsWorkRequest
        )
    }


}

