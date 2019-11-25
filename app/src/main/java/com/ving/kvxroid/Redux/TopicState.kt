package com.ving.kvxroid.Redux

import com.ving.kvxroid.Selection.TopicRealm
import com.ving.kvxroid.Services.RealmInteractor
import org.rekotlin.Action
import org.rekotlin.Middleware
import org.rekotlin.StateType

data class TopicState(val counter: Int = 0): StateType {
    companion object {}

    data class TopicActionAdd(val unit: Unit = Unit): Action
    data class TopicActionConnect(val unit: Unit = Unit): Action
    data class TopicActionUpdate(val unit: Unit = Unit): Action
    data class ItemListStateLoad(val unit: Unit = Unit): Action

}

fun TopicState.Companion.reducer(action: Action, state: TopicState?): TopicState {
    var state = state ?: TopicState()
    when (action) {

    }
    return state
}

fun TopicState.Companion.middleware(): Middleware<AppState> = { dispatch, getState ->
    { next ->
        { action ->
            (action as? TopicActionAdd)?.let {
                next(action)
                val realmInteractor = RealmInteractor()
                realmInteractor.addTopic(TopicRealm()) {
                    dispatch(TopicState.ItemListStateLoad())

                }
            }

            (action as? TopicState.TopicActionConnect)?.let {
                next(action)
                val appState = getState()

                val task = appState?.tasks?.get("abc")
                task?.listener = {
                    dispatch(TopicState.TopicActionUpdate())
                }
            } ?: next(action)
        }
    }
}