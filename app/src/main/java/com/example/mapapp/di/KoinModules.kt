package com.example.mapapp.di

import androidx.room.Room
import com.example.mapapp.data.location_provider.LocationProvider
import com.example.mapapp.data.location_provider.LocationProviderImpl
import com.example.mapapp.data.repository.MarkersRepositoryImpl
import com.example.mapapp.data.room.MarkerDatabase
import com.example.mapapp.domain.repository.MarkersRepository
import com.example.mapapp.ui.map.MapViewModel
import com.example.mapapp.ui.markers.MarkersListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModules {
    private const val DB_NAME = "MarkerDataBase"

    val repositoryModule = module {
        single<MarkersRepository> {
            MarkersRepositoryImpl(
                 markerDataBase = get()
            )
        }

        single<LocationProvider> { LocationProviderImpl(androidContext()) }
    }

    val viewModulesModule = module {
        viewModel {
            MapViewModel(markersRepository = get())
        }

        viewModel{
            MarkersListViewModel(markersRepository = get())
        }
    }

    fun storageModule() = module {
        single<MarkerDatabase> {
            Room.databaseBuilder(androidContext(), MarkerDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}