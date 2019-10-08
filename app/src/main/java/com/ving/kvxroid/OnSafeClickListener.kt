package com.ving.kvxroid

import android.os.SystemClock
import android.view.View

class OnSafeClickListener(
    private var defaultInterval: Int = 1000,
    private val onSafeClick: (View) -> Unit
) : View.OnClickListener {

    private var lastTimeClicked: Long = 0

    override fun onClick(v: View) {
        val time = SystemClock.elapsedRealtime() - lastTimeClicked

        if (time >= defaultInterval) {
            lastTimeClicked = SystemClock.elapsedRealtime()
            onSafeClick(v)
        }
    }
}

fun View.setOnSafeClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = OnSafeClickListener {
        onSafeClick(it)
    }

    setOnClickListener(safeClickListener)
}