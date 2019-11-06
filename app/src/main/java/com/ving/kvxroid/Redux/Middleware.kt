package com.ving.kvxroid.Redux

import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.ItemList.Detail.ItemImageViewModel
import com.ving.kvxroid.Selection.ItemRealm
import com.ving.kvxroid.Selection.TopicRealm
import com.ving.kvxroid.Services.FirestoreService
import com.ving.kvxroid.Services.RealmInteractor

import org.rekotlin.Middleware
import java.util.*
import kotlin.collections.ArrayList

internal val connectionMiddleware: Middleware<AppState> = { dispatch, getState ->
    { next ->
        { action ->
            (action as? ConnectionActionAdd)?.let {
                //                it.value += " Second Middleware"
                next(action)
                dispatch(ConnectionActionLoad())
            } ?: next(action)
        }
    }
}


internal val topicMiddleware: Middleware<AppState> = { dispatch, getState ->
    { next ->
        { action ->
            (action as? TopicActionAdd)?.let {
                //                it.value += " Second Middleware"
                next(action)
                val realmInteractor = RealmInteractor()
                val topic = TopicRealm()
                topic.id = UUID.randomUUID().toString()
                topic.name = "Item 1"

                realmInteractor.addTopic(topic) {
                    dispatch(TopicActionLoad())

                }
            }

            (action as? TopicActionRemove)?.let {

                val realmInteractor = RealmInteractor()

                realmInteractor.deleteTopic(action.id) {
                    dispatch(TopicActionLoad())
                }
            }

            (action as? TopicActionConnect)?.let {
                //                it.value += " Second Middleware"
                next(action)

                val appState = getState()


                val task = appState?.tasks?.get("abc")
                task?.listener = {
                    dispatch(TopicActionUpdate())

                }
            } ?: next(action)
        }
    }
}

internal val itemMiddleware: Middleware<AppState> = { dispatch, getState ->
    { next ->
        { action ->
            (action as? ItemActionAdd)?.let {
                //                it.value += " Second Middleware"
                next(action)
                val realmInteractor = RealmInteractor()
                var item = ItemRealm()
                item.id = UUID.randomUUID().toString()

                item.name = action.name
                realmInteractor.addItem(item) {
                    dispatch(ItemActionLoad())

                }
            }

            (action as? ItemActionRemove)?.let {

                val realmInteractor = RealmInteractor()

                realmInteractor.deleteItem(action.id) {
                    dispatch(ItemActionLoad())

                }

            } ?: next(action)
        }
    }
}

internal val imagesMiddleware: Middleware<AppState> = { dispatch, getState ->
    { next ->
        { action ->
            (action as? ItemImageActionLoad)?.let {
                next(action)
                val service = FirestoreService()
                service.getItems { items ->
                    val list = items.map {
                        ItemImageViewModel(
                            it.id,
                            it.name,
                            it.imageUrl,
                            isSelected = false
                        )
                    }

                    val action = ItemImageActionFetch()
                    action.list.addAll(list)
                    dispatch(action)

                }
            }

            (action as? ItemImageActionSelect)?.let {
                //                next(action)
                val itemImageList = getState()?.itemImageList ?: arrayListOf()

                val newItems = itemImageList.map {
                    if (it is ItemImageViewModel) {
                        if (it.id == action.id) {
                            ItemImageViewModel(
                                it.id,
                                it.name,
                                it.imageUrl,
                                isSelected = true
                            )
                        } else {
                            ItemImageViewModel(
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

                val action = ItemImageActionFetch()
                action.list.addAll(newItems ?: ArrayList())
                dispatch(action)


            }

                ?: next(action)
        }
    }
}