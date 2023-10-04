package com.example.mapapp

import android.app.Application
import com.example.mapapp.di.KoinModules
import com.yandex.mapkit.MapKitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(getString(R.string.API_KEY))
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    KoinModules.repositoryModule,
                    KoinModules.viewModulesModule,
                    KoinModules.storageModule()
                )
            )
        }
    }
}