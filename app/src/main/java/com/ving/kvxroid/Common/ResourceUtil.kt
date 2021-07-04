package com.ving.kvxroid.Common

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager

object ResourceUtil {

    private val context: Context get() = BaseApplication.INSTANCE.applicationContext

    @SuppressLint("HardwareIds")
    var getDeviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}

fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}