package com.ving.kvxroid.Common

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

object ResourceUtil {

    private val context: Context get() = BaseApplication.INSTANCE.applicationContext

    @SuppressLint("HardwareIds")
    var getDeviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}