package com.ving.kvxroid.Redux

import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Common.BaseApplication
import com.ving.kvxroid.Detail.ItemDetailHeaderViewModel
import com.ving.kvxroid.Detail.ItemDetailPlusViewModel
import com.ving.kvxroid.Detail.ItemDetailSwitchViewModel
import com.ving.kvxroid.Selection.*
import com.ving.kvxroid.Services.RealmInteractor
import com.ving.kvxroid.Services.TopicConnector
import org.rekotlin.Action
import io.realm.Realm
import java.util.*
import kotlin.collections.ArrayList


fun counterReducer(action: Action, state: AppState?): AppState {
    // if no state has been provided, create the default state
    var state = state ?: AppState()

    when(action){
        is CounterActionIncrease -> {
            state = state.copy(counter = state.counter + 1)
        }
        is CounterActionDecrease -> {
            state = state.copy(counter = state.counter - 1)
        }
        is ItemListStateLoad -> {

            val items: ArrayList<AnyObject> = ArrayList()
            items.add(ItemDetailHeaderViewModel("Header abc"))
            items.add(ItemDetailSwitchViewModel("switch 1"))
            items.add(ItemDetailSwitchViewModel("switch 2"))
            items.add(ItemDetailPlusViewModel())

            val topicList = RealmInteractor().getTopics()
            val list = topicList.forEach { topicRealm ->
                val viewModel = ItemDetailSwitchViewModel("sffffff")
                items.add(viewModel)
            }

            state = state.copy(itemDetailList = items)

        }

        is ConnectionActionAdd -> {
            val items: ArrayList<AnyObject> = ArrayList()

            val realmInteractor = RealmInteractor()
            realmInteractor.connectRealm()


        }

        is ConnectionActionLoad -> {
//            val items: ArrayList<AnyObject> = ArrayList()

//            items.add(SelectionTypeViewModel("Type here"))
//            items.add(SelectionServerViewModel("Server here"))
//            items.add(SelectionServerViewModel("Server here aaaa"))
            val connectionList = RealmInteractor().getConnections()
            val items = connectionList.map { connectionRealm ->
                SelectionServerViewModel("sffffff")
            }

            state = state.copy(connectionList = items)

        }

        is TopicActionAdd -> {
            val realmInteractor = RealmInteractor()
            state.tasks.put("three", realmInteractor)
            realmInteractor.addTopic()

        }

        is TopicActionConnect -> {
            val connector = TopicConnector()
            connector.connect()
        }
    }

    return state
}



