package com.ving.kvxroid.Redux

import com.ving.kvxroid.Models.Server
import com.ving.kvxroid.Models.Topic
import com.ving.kvxroid.Services.TopicRealm
import com.ving.kvxroid.Services.RealmInteractor
import com.ving.kvxroid.Services.TopicConnector
import org.rekotlin.Action
import org.rekotlin.Middleware
import org.rekotlin.StateType

data class TopicState(
    var topic: Topic? = null,
    var topics: List<Topic>? = emptyList(),
    var editableTopic: Topic? = null,
    val tasks: MutableMap<String, TopicConnector> = mutableMapOf<String, TopicConnector>()

    ): StateType {
    companion object {}

    data class UpdateTopicAction(val unit: Unit = Unit): Action{
        var topic: Topic? = null
    }
    data class LoadTopicsAction(val unit: Unit = Unit): Action
    data class AddTopicAction(val unit: Unit = Unit): Action {
        var topic: Topic? = null
    }
    data class LoadTopicAction(val unit: Unit = Unit): Action {
        var id: String = ""
    }

    data class FetchTopicAction(val unit: Unit = Unit): Action {
        var topic: Topic? = null
    }

    data class FetchEditableTopicAction(val unit: Unit = Unit): Action {
        var topic: Topic? = null
    }

    data class UpdateTaskAction(val unit: Unit = Unit): Action {
        var topic: Topic? = null
    }

    data class FetchTaskAction(val unit: Unit = Unit): Action {
        var topicId: String = ""
        var task: TopicConnector? = null
    }

    data class PublishAction(val unit: Unit = Unit): Action {
        var topicId: String = ""
        var message: String = ""
    }

    data class StopAllTasksAction(val unit: Unit = Unit): Action

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

        is TopicState.FetchTopicAction -> {
            state = state.copy(topic = action.topic)
        }

        is TopicState.FetchEditableTopicAction -> {
            state = state.copy(editableTopic = action.topic)
        }

        is TopicState.FetchTaskAction -> {
            val topicId = action?.topicId ?: ""
            if (state.tasks.get(topicId) == null) {
//                action.task?.connect()
                action.task?.let {
                    state.tasks.put(topicId, it)
                }
            }
        }

        is TopicState.PublishAction -> {
            val task = state.tasks.get(action.topicId)
            task?.publish(action.message)
        }

        is TopicState.StopAllTasksAction -> {
//            state.tasks.forEach {
//                it.value.disconnect()
//            }
            state.tasks.clear()

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
                    Topic(it.id ?: "",
                        it.name ?: "",
                        it.topic ?: "",
                        it.value ?: "",
                        it.time ?: "",
                        it.serverId ?: "",
                        it.type ?: "")
                }

                val stopAllTasksAction = TopicState.StopAllTasksAction()
                dispatch(stopAllTasksAction)

                topics.forEach {
                    val updateTaskAction = TopicState.UpdateTaskAction()
                    updateTaskAction.topic = it
                    dispatch(updateTaskAction)
                }

                val fetchTopicsAction = TopicState.FetchTopicsAction()
                fetchTopicsAction.topics = topics
                dispatch(fetchTopicsAction)
            }

            (action as? TopicState.UpdateTopicAction)?.let {
                val interactor = RealmInteractor()
                var itemRef = TopicRealm()
                action.topic?.let {
                    itemRef.id = it.id
                    itemRef.name = it.name
                    itemRef.value = it.value
                    itemRef.serverId = it.serverId
                    itemRef.type = it.type
                    itemRef.topic = it.topic
                    itemRef.time = it.time
                    itemRef.retain = ""
                    itemRef.itemId = ""
                    itemRef.qos = it.qos

                }
                interactor.updateTopic(itemRef) {

                    val loadTopicAction = TopicState.LoadTopicAction()
                    loadTopicAction.id = action.topic?.id ?: ""
                    dispatch(loadTopicAction)
                }
            }

            (action as? TopicState.RemoveTopicAction)?.let {
                val interactor = RealmInteractor()
                interactor.deleteTopic(action.id) {
                    dispatch(TopicState.LoadTopicsAction())
                }
            }

            (action as? TopicState.LoadTopicAction)?.let {
//                val interactor = RealmInteractor()
//                interactor.getTopic(it.id) {
//                    it?.let {
//                        val fetchTopicAction = TopicState.FetchTopicAction()
//                        fetchTopicAction.topic = Topic(it.id ?: "",
//                            it.name ?: "",
//                            it.topic?: "",
//                            it.value ?: "",
//                            it.time ?: "",
//                            it.serverId ?: "",
//                            it.type ?: "",
//                            "")
//                        dispatch(fetchTopicAction)
//                        val action2 = ServerState.LoadServerAction()
//                        action2.id = it.serverId ?: ""
//                        dispatch(action2)
//
//                        val loadTopicsAction = TopicState.LoadTopicsAction()
////                        dispatch(loadTopicsAction)
//
////                        val updateTaskAction = TopicState.UpdateTaskAction()
////                        updateTaskAction.topic = fetchTopicAction.topic
////                        dispatch(updateTaskAction)
//                    }
//                }

                val topics = getState()?.topicState?.topics
                val topic = topics?.filter { topic ->
                   topic.id == it.id }?.first()
                topic?.let {
                    val fetchTopicAction = TopicState.FetchTopicAction()
                    fetchTopicAction.topic = it
                    dispatch(fetchTopicAction)
                    val action2 = ServerState.LoadServerAction()
                    action2.id = it.serverId ?: ""
                    dispatch(action2)

                }

            }

            (action as? TopicState.UpdateTaskAction)?.let {
                val topic = action.topic ?: Topic()
                val interactor = RealmInteractor()
                interactor.getServer(topic.serverId) {
                    val server = Server()
                    server.id = it?.id ?: ""
                    server.name = it?.name ?: ""
                    server.url = it?.url ?: ""
                    server.user = it?.user ?: ""
                    server.password = it?.password ?: ""
                    server.port = it?.port ?: ""
                    server.sslPort = it?.sslPort ?: ""

                    val state = getState()?.topicState

                    var task = state?.tasks?.get(action.topic?.id)


                    if (task != null) {

                    } else {
                        task = TopicConnector()
                        task.configure(topic, server)
                        task.didReceiveMessage  = {
                            if (task.topic?.value != it) {
                                task.topic?.value = it
                                    val updateTopicAction = TopicState.UpdateTopicAction()
                                    updateTopicAction.topic = task.topic
                                    dispatch(updateTopicAction)
                            }
                        }
                    }

                    val fetchTaskAction = TopicState.FetchTaskAction()
                    fetchTaskAction.topicId = topic.id
                    fetchTaskAction.task = task
                    dispatch(fetchTaskAction)

                }
            }

            (action as? TopicState.AddTopicAction)?.let {
                next(action)
                val itemRef = TopicRealm()
                action.topic?.let {
                    itemRef.id = it.id
                    itemRef.name = it.name
                    itemRef.value = it.value
                    itemRef.serverId = it.serverId
                    itemRef.type = it.type
                    itemRef.topic = it.topic
                    itemRef.time = it.time
                    itemRef.retain = ""
                    itemRef.itemId = ""
                    itemRef.qos = it.qos
                }

                val realmInteractor = RealmInteractor()
                realmInteractor.addTopic(itemRef) {
                    dispatch(TopicState.LoadTopicsAction())
//                    dispatch(TopicState.LoadTopicAction())

                }
            }
                ?: next(action)
        }
    }
}