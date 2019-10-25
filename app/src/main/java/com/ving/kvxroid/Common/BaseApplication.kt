package com.ving.kvxroid.Common

import android.app.Application

open class BaseApplication : Application() {

    companion object {
        lateinit var INSTANCE: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

}