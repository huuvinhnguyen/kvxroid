package com.ving.kvxroid.Redux

import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Models.Topic
import com.ving.kvxroid.Selection.TopicRealm
import com.ving.kvxroid.Services.RealmInteractor
import com.ving.kvxroid.Services.TopicConnector
import org.rekotlin.Action
import org.rekotlin.Middleware
import org.rekotlin.StateType

data class TopicState(
    val counter: Int = 0,
    var topic: Topic? = null,
    var topics: List<Topic> = emptyList(),
    val tasks: MutableMap<String, TopicConnector> = mutableMapOf<String, TopicConnector>()


    ): StateType {
    companion object {}

    data class TopicActionConnect(val unit: Unit = Unit): Action
    data class TopicActionUpdate(val unit: Unit = Unit): Action
    data class LoadTopicsAction(val unit: Unit = Unit): Action
    data class AddTopicAction(val unit: Unit = Unit): Action {
        var topic: Topic? = null
    }
    data class RemoveTopicAction(val unit: Unit = Unit): Action {
        var id: String = ""
    }
    data class FetchTopicsAction(val unit: Unit = Unit): Action {
        var topics: List<Topic> = emptyList()
    }


}

fun TopicState.Companion.reducer(action: Action, state: TopicState?): TopicState {
    var state = state ?: TopicState()
    when (action) {

        is TopicState.FetchTopicsAction -> {
            state = state.copy(topics = action.topics)
        }

    }
    return state
}

fun TopicState.Companion.middleware(): Middleware<AppState> = { dispatch, getState ->
    { next ->
        { action ->

            (action as? TopicState.LoadTopicsAction)?.let {
                val topicList = RealmInteractor().getTopics()
                val topics = topicList.map {
                    Topic(it.id ?: "", it.name ?: "", it.topic ?: "", it.value ?: "", it.time ?: "", it.serverId ?: "", it.kind ?: "")
                }

                val action = TopicState.FetchTopicsAction()
                action.topics = topics
                dispatch(action)
            }

            (action as? TopicState.RemoveTopicAction)?.let {
                val interactor = RealmInteractor()
                interactor.deleteTopic(action.id) {
                    dispatch(TopicState.LoadTopicsAction())
                }
            }

            (action as? TopicState.AddTopicAction)?.let {
                next(action)
                val realmInteractor = RealmInteractor()
                realmInteractor.addTopic(TopicRealm()) {
                    dispatch(TopicState.LoadTopicsAction())
//                    dispatch(TopicState.LoadTopicAction())

                }
            }

            (action as? TopicState.TopicActionConnect)?.let {
                next(action)
                val appState = getState()

                val task = appState?.tasks?.get("abc")
                task?.listener = {
                    dispatch(TopicState.TopicActionUpdate())
                }
            }
                ?: next(action)
        }
    }
}