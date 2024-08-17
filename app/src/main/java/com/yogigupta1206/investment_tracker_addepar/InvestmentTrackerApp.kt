package com.yogigupta1206.investment_tracker_addepar

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class InvestmentTrackerApp: Application() {

    companion object {
        private const val TAG = "InvestmentTrackerApp"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: InvestmentTrackerApp")
    }
}