package com.example.memes

import android.app.Application
import com.example.memes.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MemeApplication:Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            //Required to give dependency application context when required
            androidContext(this@MemeApplication)
            modules(appModule)
        }
    }
}