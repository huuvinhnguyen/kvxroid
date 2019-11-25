    package com.ving.kvxroid.Redux

import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Detail.ItemDetailHeaderViewModel
import com.ving.kvxroid.Detail.ItemDetailPlusViewModel
import com.ving.kvxroid.Detail.ItemDetailSwitchViewModel
import com.ving.kvxroid.ItemList.Detail.ItemViewModel
import com.ving.kvxroid.Selection.ItemRealm
import com.ving.kvxroid.Selection.SelectionServerViewModel
import com.ving.kvxroid.Services.RealmInteractor
import com.ving.kvxroid.Services.TopicConnector
import org.rekotlin.Action
import org.rekotlin.Middleware
import org.rekotlin.StateType
import java.util.*

data class ItemState(
    val itemList: List<Any> = emptyList(),
    var itemDetailList: List<Any> = emptyList()
) : StateType {
    companion object {}
    data class ItemActionLoad(val unit: Unit = Unit): Action
    data class ItemListStateLoad(val unit: Unit = Unit) : Action
    data class ItemDetailLoad(val unit: Unit = Unit) : Action
    data class ItemActionAdd(val unit: Unit = Unit) : Action {
        var name: String? = null
    }
}

fun ItemState.Companion.reducer(action: Action, state: ItemState?): ItemState {

    var state = state ?: ItemState()
    when(action){

        is ItemState.ItemListStateLoad -> {

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


        is ItemState.ItemActionLoad -> {
            val items: ArrayList<AnyObject> = ArrayList()
//            items.add(ItemViewModel("bye bye 1"))
//            items.add(ItemViewModel("hello helo 2"))
//            items.add(ItemViewModel("hello helo 2"))
//
//            items.add(ItemDetailPlusViewModel())

            val interactor = RealmInteractor()

            val list = interactor.getItems().map { itemRealm ->
                ItemViewModel(itemRealm.name ?: "")
            }

            items.addAll(list)
            items.add(ItemDetailPlusViewModel())

            state = state.copy(itemList = items)


        }
    }
    return state
}


fun ItemState.Companion.middleware(): Middleware<AppState> = { dispatch, getState ->
    { next ->
        { action ->
            (action as? ItemState.ItemActionAdd)?.let {
                //                it.value += " Second Middleware"
                next(action)
                val realmInteractor = RealmInteractor()
                var item = ItemRealm()
                item.id  = UUID.randomUUID().toString()

                item.name = action.name
                realmInteractor.addItem(item) {
                    dispatch(ItemState.ItemActionLoad())

                }
            } ?: next(action)
        }
    }
}
