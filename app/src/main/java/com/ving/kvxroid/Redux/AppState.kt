package com.ving.kvxroid.Redux

import com.ving.kvxroid.ItemList.Detail.ItemNameViewModel
import com.ving.kvxroid.Models.Item
import com.ving.kvxroid.Services.TopicConnector
import org.rekotlin.StateType
import org.rekotlinrouter.HasNavigationState
import org.rekotlinrouter.NavigationState

data class AppState(
    override var navigationState: NavigationState = NavigationState(),
    var itemState: ItemState = ItemState(),
    var topicState: TopicState = TopicState(),
    val counter: Int = 0,
    val itemList: List<Any> = emptyList(),
    var itemDetailList: List<Any> = emptyList(),
    var connectionList: List<Any> = emptyList(),
    val tasks: MutableMap<String, TopicConnector> = mutableMapOf<String, TopicConnector>(),
//    val itemImageList: ArrayList<AnyObject> = arrayListOf(),
//    var images: ArrayList<AnyObject> = arrayListOf(),

    val itemNameViewModel: ItemNameViewModel? = null,
    val itemViewModel: Item? = null

): StateType, HasNavigationState
