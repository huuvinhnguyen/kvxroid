package com.ving.kvxroid.Redux

import org.rekotlin.StateType

data class AppState (
    val counter: Int = 0,
    val itemList: List<Any> = emptyList(),
    var itemDetailList: List<Any> = emptyList(),
    var connectionList: List<Any> = emptyList()

): StateType