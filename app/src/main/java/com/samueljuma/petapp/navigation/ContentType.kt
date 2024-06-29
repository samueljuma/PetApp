package com.samueljuma.petapp.navigation

sealed interface ContentType {
    object List : ContentType

    object ListAndDetail : ContentType
}