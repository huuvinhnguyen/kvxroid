package com.ving.kvxroid.Redux

import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.ItemList.Detail.ItemNameViewModel
import com.ving.kvxroid.Services.TopicConnector
import org.rekotlin.StateType

data class AppState(
    val counter: Int = 0,
    val itemList: List<Any> = emptyList(),
    var itemDetailList: List<Any> = emptyList(),
    var connectionList: List<Any> = emptyList(),
    val tasks: MutableMap<String, TopicConnector> = mutableMapOf<String, TopicConnector>(),
    val itemImageList: ArrayList<AnyObject> = arrayListOf(),
    val itemNameViewModel: ItemNameViewModel? = null

): StateType