package com.example.perpustakaan

import android.app.Application
import com.example.perpustakaan.Container.AppContainer
import com.example.perpustakaan.Container.PerpustakaanAppContainer

class PerpustakaanApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        // Menginisialisasi AppContainer dengan DefaultAppContainer
        container = PerpustakaanAppContainer()
    }
}
