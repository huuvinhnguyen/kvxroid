package com.ving.kvxroid.Redux

import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Detail.*
import com.ving.kvxroid.ItemList.Detail.ItemViewModel
import com.ving.kvxroid.Selection.*
import com.ving.kvxroid.Services.RealmInteractor
import com.ving.kvxroid.Services.TopicConnector
import org.rekotlin.Action
import kotlin.collections.ArrayList


fun counterReducer(action: Action, state: AppState?): AppState {
    // if no state has been provided, create the default state
    var state = state ?: AppState()

    when (action) {
        is CounterActionIncrease -> {
            state = state.copy(counter = state.counter + 1)
        }
        is CounterActionDecrease -> {
            state = state.copy(counter = state.counter - 1)
        }
        is TopicListLoadAction -> {

            val items: ArrayList<AnyObject> = ArrayList()
//            items.add(ItemDetailHeaderViewModel("Header abc"))
//            items.add(ItemDetailSwitchViewModel("switch 1"))
//            items.add(ItemDetailSwitchViewModel("switch 2"))



            val topicList = RealmInteractor().getTopics()
            val list = topicList.forEach {
                val viewModel = ItemDetailSwitchViewModel(it.id ?:"", it.name ?:"")
                items.add(viewModel)
            }

            items.add(ItemDetailChartViewModel())
            items.add(ItemLineChartViewModel())

            items.add(ItemDetailPlusViewModel())
            items.add(ItemDetailTrashViewModel())

            state = state.copy(itemDetailList = items)

        }

        is ConnectionActionAdd -> {
            val items: ArrayList<AnyObject> = ArrayList()

            val realmInteractor = RealmInteractor()
            realmInteractor.connectRealm()


        }

        is ConnectionActionLoad -> {

            val connectionList = RealmInteractor().getConnections()
            val items = connectionList.map { connectionRealm ->
                SelectionServerViewModel("sffffff")
            }

            state = state.copy(connectionList = items)

        }


        is TopicListConnectAction -> {
            val connector = TopicConnector()
            state.tasks.put("abc", connector)

            connector.connect()
            connector.receiveMessages()
        }

        is ItemLoadAction -> {
            state = state.copy(itemViewModel = action.itemViewModel)

        }

        is ItemListAction -> {
            val items: ArrayList<AnyObject> = ArrayList()

            val interactor = RealmInteractor()

            val list = interactor.getItems().map { itemRealm ->
                ItemViewModel(itemRealm.id ?: "", itemRealm.name ?: "")
            }

            items.addAll(list)
            items.add(ItemDetailPlusViewModel())

            state = state.copy(itemList = items)

        }

        is ItemImageActionFetch -> {
            state = state.copy(itemImageList = action.list)
        }

        is ItemNameActionLoad -> {
            state = state.copy(itemNameViewModel = action.itemNameViewModel)
        }

    }


    return state
}





