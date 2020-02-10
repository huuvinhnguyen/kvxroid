package com.ving.kvxroid.Redux

import com.ving.kvxroid.Models.Server
import com.ving.kvxroid.Services.RealmInteractor
import com.ving.kvxroid.Services.ServerRealm
import com.ving.kvxroid.Services.TopicConnector
import org.rekotlin.Action
import org.rekotlin.Middleware
import org.rekotlin.StateType

data class ServerState(

    val server: Server? = null,
    val servers: List<Server> = emptyList()

): StateType {
    companion object {}

    data class AddServerAction(val unit: Unit = Unit): Action {
        var server: Server? = null
    }
    data class UpdateServerAction(val unit: Unit = Unit): Action {
        var server: Server? = null
    }
    data class RemoveServerAction(val unit: Unit = Unit): Action {
        var id: String = ""
    }
    data class LoadServerAction(val unit: Unit = Unit): Action {
        var id: String = ""
    }
    data class LoadServersAction(val unit: Unit = Unit): Action
}

fun ServerState.Companion.reducer(action: Action, state: ServerState?): ServerState {
    var state = state ?: ServerState()
    when (action) {

        is ServerState.LoadServersAction -> {
            val interactor = RealmInteractor()
            val servers = interactor.getServers().map {
                Server(it.id ?: "",
                    it.name ?: "",
                    it.url ?: "",
                    it.user ?: "",
                    it.password ?: "",
                    it.port ?: "",
                    it.sslPort ?: "")
            }


            state = state.copy(servers = servers)
        }

        is ServerState.LoadServerAction -> {
            val interactor = RealmInteractor()
            interactor.getServer(action.id) {
                it?.let {
                    val server = Server(it.id ?: "",
                        it.name ?: "",
                        it.url ?: "",
                        it.user ?: "",
                        it.password ?: "",
                        it.port ?: "",
                        it.sslPort ?: "")
                    state = state.copy(server = server)
                }
            }
        }
    }
    return state
}

fun ServerState.Companion.middleware(): Middleware<AppState> = { dispatch, getState ->
    { next ->
        { action ->

            (action as? ServerState.AddServerAction)?.let {

                val interactor = RealmInteractor()
                var item = ServerRealm()
                action.server?.let {
                    item.id = it.id
                    item.name = it.name
                    item.url = it.url
                    item.user = it.user
                    item.password = it.password
                    item.port = it.port
                    item.sslPort = it.sslPort
                    interactor.addServer(item) {
                        dispatch(ServerState.LoadServersAction())
                    }
                }
            }

            (action as? ServerState.UpdateServerAction)?.let {

                val interactor = RealmInteractor()
                var item = ServerRealm()
                action.server?.let {
                    item.id = it.id
                    item.name = it.name
                    item.url = it.url
                    item.user = it.user
                    item.password = it.password
                    item.port = it.port
                    item.sslPort = it.sslPort
                    interactor.updateServer(item) {
                        val loadServerAction = ServerState.LoadServerAction()
                        loadServerAction.id = it
                        dispatch(loadServerAction)
                    }
                }
            }

            (action as? ServerState.RemoveServerAction)?.let {
                val interactor = RealmInteractor()
                action.id?.let {
                    interactor.deleteServer(it) {
                        val loadServersAction = ServerState.LoadServersAction()
                        dispatch(loadServersAction)
                    }
                }
            }
                ?: next(action)
        }
    }
}