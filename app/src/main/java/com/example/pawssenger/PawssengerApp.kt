package com.example.pawssenger

import android.app.Application
import com.google.firebase.FirebaseApp

class PawssengerApp: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this )
    }
}