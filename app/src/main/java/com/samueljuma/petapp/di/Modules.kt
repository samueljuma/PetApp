package com.samueljuma.petapp.di

import com.samueljuma.petapp.data.PetsRepository
import com.samueljuma.petapp.data.PetsRepositoryImpl
import com.samueljuma.petapp.viewmodel.PetsViewModel
import org.koin.dsl.module

val appModules = module {
    single <PetsRepository> { PetsRepositoryImpl() }
    single { PetsViewModel(get()) }
}