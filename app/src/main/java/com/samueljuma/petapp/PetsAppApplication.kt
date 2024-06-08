package com.samueljuma.petapp

import android.app.Application
import com.samueljuma.petapp.di.appModules
import org.koin.core.context.startKoin

class PetsAppApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModules)
        }
    }
}