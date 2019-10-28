package com.ving.kvxroid.Redux

import com.ving.kvxroid.Services.RealmInteractor
import org.rekotlin.Middleware

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
                realmInteractor.addTopic {
                    dispatch(ItemListStateLoad())

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