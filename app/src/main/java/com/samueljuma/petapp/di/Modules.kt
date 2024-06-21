package com.samueljuma.petapp.di

import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.samueljuma.petapp.data.CatDatabase
import com.samueljuma.petapp.data.CatsAPI
import com.samueljuma.petapp.data.PetsRepository
import com.samueljuma.petapp.data.PetsRepositoryImpl
import com.samueljuma.petapp.utils.DATABASE_NAME
import com.samueljuma.petapp.viewmodel.PetsViewModel
import com.samueljuma.petapp.workers.PetsSyncWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module
import retrofit2.Retrofit

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

val appModules = module {
    // Repository
    single <PetsRepository> { PetsRepositoryImpl(get(), get(), get()) }

    // Dispatcher.IO - performs I/O operations off the main thread
    single { Dispatchers.IO }

    // ViewModel
    single { PetsViewModel(get()) }

    // Retrofit Instance
    single{
        Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType = "application/json".toMediaType()))
            .baseUrl("https://cataas.com/api/")
            .build()
    }

    // API Service
    single { get<Retrofit>().create(CatsAPI::class.java) }

    // Database
    single {
        Room.databaseBuilder(
            androidContext(), // from Koin
            CatDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    // DAO
    single { get<CatDatabase>().catDao() }

    worker { PetsSyncWorker(get(), get(), get()) }

}