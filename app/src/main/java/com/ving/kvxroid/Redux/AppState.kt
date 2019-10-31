package com.ving.kvxroid.Redux

import com.ving.kvxroid.Services.TopicConnector
import org.rekotlin.StateType

data class AppState(
    val counter: Int = 0,
    val itemList: List<Any> = emptyList(),
    var itemDetailList: List<Any> = emptyList(),
    var connectionList: List<Any> = emptyList(),
    val tasks: MutableMap<String, TopicConnector> = mutableMapOf<String, TopicConnector>(),
    val itemImageList: List<Any> = emptyList()

): StateType