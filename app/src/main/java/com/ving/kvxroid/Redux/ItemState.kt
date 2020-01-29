package com.ving.kvxroid.Redux

import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Detail.ItemDetailHeaderViewModel
import com.ving.kvxroid.Detail.ItemDetailPlusViewModel
import com.ving.kvxroid.Detail.ItemDetailSwitchViewModel
import com.ving.kvxroid.Models.Item
import com.ving.kvxroid.Models.Image
import com.ving.kvxroid.Services.ItemRealm
import com.ving.kvxroid.Services.FirestoreService
import com.ving.kvxroid.Services.RealmInteractor
import org.rekotlin.Action
import org.rekotlin.Middleware
import org.rekotlin.StateType
import java.util.*
import kotlin.collections.ArrayList

data class ItemState(
    var item: Item? = null,
    val items: List<Any> = emptyList(),
    var itemDetailList: List<Any> = emptyList(),
    var images: List<Any> = emptyList()

) : StateType {
    companion object {}

    data class LoadImagesAction(val unit: Unit = Unit) : Action {
        var images: List<Any> = emptyList()
    }

    data class ItemLoadAction(val unit: Unit = Unit) : Action {
        var id: String = ""

    }
    data class ItemListAction(val unit: Unit = Unit) : Action
    data class ItemListStateLoad(val unit: Unit = Unit) : Action
    data class ItemAddAction(val unit: Unit = Unit) : Action {
        var item: Item? = null
    }

    data class ItemRemoveAction(val unit: Unit = Unit): Action {
        var id: String = ""
    }

    data class SelectImageAction(val unit: Unit = Unit) : Action {
        var id: String = ""
    }

    data class FetchImagesAction(val unit: Unit = Unit) : Action {
        var list: ArrayList<AnyObject> = ArrayList()
    }

    data class UpdateItemImageAction(val unit: Unit = Unit) : Action {
        var imageUrl: String = ""
    }

}

fun ItemState.Companion.reducer(action: Action, state: ItemState?): ItemState {

    var state = state ?: ItemState()
    when (action) {

        is ItemState.FetchImagesAction -> {
            state = state.copy(images = action.list)
        }

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


        is ItemState.ItemLoadAction -> {

            val interactor = RealmInteractor()

            interactor.getItem(action.id) {
                val item = Item(it?.id ?: "", it?.name ?: "", it?.imageUrl ?: "")
                state = state.copy(item = item)

            }
        }

        is ItemState.ItemListAction -> {
            val items: ArrayList<AnyObject> = ArrayList()

            val interactor = RealmInteractor()

            val list = interactor.getItems().map { itemRealm ->
                Item(itemRealm.id ?: "", itemRealm.name ?: "", itemRealm.imageUrl ?: "")
            }

            items.addAll(list)
            items.add(ItemDetailPlusViewModel())

            state = state.copy(items = items)

        }

        is ItemState.UpdateItemImageAction -> {
            var item = state.item
            item?.imageUrl = action.imageUrl
            state = state.copy(item = item)
        }
    }
    return state
}


fun ItemState.Companion.middleware(): Middleware<AppState> = { dispatch, getState ->
    { next ->
        { action ->
            next(action)

            (action as? ItemState.LoadImagesAction)?.let {
                val service = FirestoreService()
                service.getItems { items ->
                    val list = items.map {
                        Image(
                            it.id,
                            it.name,
                            it.imageUrl,
                            isSelected = false
                        )
                    }

                    val action = ItemState.FetchImagesAction()
                    action.list.addAll(list)
                    dispatch(action)

                }

            }

            (action as? ItemState.ItemAddAction)?.let {
                next(action)
                val realmInteractor = RealmInteractor()
                var item = ItemRealm()
                item.id = UUID.randomUUID().toString()

                action.item?.let {
                    item.name = it.name
                    item.imageUrl = it.imageUrl
                    realmInteractor.addItem(item) {
                        dispatch(ItemState.ItemListAction())

                    }

                }


            }

            (action as? ItemUpdateAction)?.let {

                val realmInteractor = RealmInteractor()

                realmInteractor.updateItem(ItemRealm()) {
                    dispatch(ItemState.ItemListAction())

                }

            }

            (action as? ItemState.ItemRemoveAction)?.let {

                val realmInteractor = RealmInteractor()

                realmInteractor.deleteItem(action.id) {
                    dispatch(ItemState.ItemListAction())

                }

            }

            (action as? ItemState.SelectImageAction)?.let {
                next(action)
                val itemImageList = getState()?.itemState?.images ?: arrayListOf()

                val newItems = itemImageList.map {
                    if (it is Image) {
                        if (it.id == action.id) {
                            Image(
                                it.id,
                                it.name,
                                it.imageUrl,
                                isSelected = true
                            )
                        } else {
                            Image(
                                it.id,
                                it.name,
                                it.imageUrl,
                                isSelected = false
                            )
                        }

                    } else {
                        null
                    }
                } as? ArrayList<AnyObject> ?: arrayListOf()

                val action = ItemState.FetchImagesAction()
                action.list.addAll(newItems ?: ArrayList())
                dispatch(action)

            }

                ?: next(action)
        }
    }
}
