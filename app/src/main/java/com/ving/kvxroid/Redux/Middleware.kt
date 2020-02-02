package com.ving.kvxroid.Redux

import com.ving.kvxroid.Models.Image
import com.ving.kvxroid.Services.ItemRealm
import com.ving.kvxroid.Services.TopicRealm
import com.ving.kvxroid.Services.FirestoreService
import com.ving.kvxroid.Services.RealmInteractor

import org.rekotlin.Middleware
import java.util.*

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
                    dispatch(TopicListLoadAction())

                }
            }

            (action as? TopicActionRemove)?.let {

                val realmInteractor = RealmInteractor()

                realmInteractor.deleteTopic(action.id) {
                    dispatch(TopicListLoadAction())
                }
            }

            (action as? TopicListConnectAction)?.let {
                //                it.value += " Second Middleware"
                next(action)

                val appState = getState()


                val task = appState?.tasks?.get("abc")
                task?.didReceiveMessage = {
                    dispatch(TopicListUpdateAction())

                }
            } ?: next(action)
        }
    }
}

internal val itemMiddleware: Middleware<AppState> = { dispatch, getState ->
    { next ->
        { action ->
//            (action as? ItemActionAdd)?.let {
//                //                it.value += " Second Middleware"
//                next(action)
//                val realmInteractor = RealmInteractor()
//                var item = ItemRealm()
//                item.id = UUID.randomUUID().toString()
//
//                item.name = action.name
//                realmInteractor.addItem(item) {
//                    dispatch(ItemListAction())
//
//                }
//            }

//            (action as? ItemActionRemove)?.let {
//
//                val realmInteractor = RealmInteractor()
//
//                realmInteractor.deleteItem(action.id) {
//                    dispatch(ItemListAction())
//
//                }
//
//            }

            (action as? ItemUpdateAction)?.let {

                val realmInteractor = RealmInteractor()

                realmInteractor.updateItem(ItemRealm()) {
                    dispatch(ItemListAction())

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
                        Image(
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



                ?: next(action)
        }
    }
}