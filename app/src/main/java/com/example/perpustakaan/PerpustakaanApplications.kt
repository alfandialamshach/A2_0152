package com.example.perpustakaan

import android.app.Application
import com.example.perpustakaan.Container.AppContainer
import com.example.perpustakaan.Container.BukuContainer

class PerpustakaanApplications: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container=BukuContainer()
    }
}