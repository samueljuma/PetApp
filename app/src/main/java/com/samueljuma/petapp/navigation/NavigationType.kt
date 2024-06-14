package com.samueljuma.petapp.navigation

/**
 * We use a sealed interface since we do not need to hold any state in our NavigationType.
 * We also do not need to pass any properties to our NavigationType.
 */
sealed interface NavigationType {
    object BottomNavigation : NavigationType
    object NavigationDrawer : NavigationType
    object NavigationRail : NavigationType
}