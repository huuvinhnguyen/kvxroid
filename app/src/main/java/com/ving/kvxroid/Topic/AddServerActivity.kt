package com.ving.kvxroid.Topic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Models.Server
import com.ving.kvxroid.Models.Topic
import com.ving.kvxroid.R
import com.ving.kvxroid.Redux.ServerState
import com.ving.kvxroid.Redux.TopicState
import com.ving.kvxroid.Redux.mainStore
import com.ving.kvxroid.Selection.ServerSelectionActivity

import kotlinx.android.synthetic.main.activity_add_server.recyclerView
import org.rekotlin.StoreSubscriber
import java.util.*
import kotlin.collections.ArrayList

class AddServerActivity : AppCompatActivity(), StoreSubscriber<ServerState> {

    override fun newState(state: ServerState) {
        state.server?.let {
            serverViewModel.id = it.id
            serverViewModel.name = it.name
            serverViewModel.server = it.url
            serverViewModel.user = it.user
            serverViewModel.password = it.password
            serverViewModel.port = it.port
            serverViewModel.sslPort = it.sslPort
        }

        val items: ArrayList<AnyObject> = ArrayList()
        items.add(serverViewModel)
        if (mode == Mode.Edit) {
            items.add(AddServerAdapter.ServerFooterViewModel())

        }

        val adapter = AddServerAdapter(ArrayList()).apply {
            onSaveClick = ::handleSaveServer
            onSelectClick = ::handleSelectServer
            onTrashClick = ::handleDeleteServer

        }

        recyclerView.adapter = adapter

        adapter.setItems(items)

    }

    enum class Mode(var topicId: String, var serverId: String) {
        Add(topicId = "", serverId = ""),
        Edit("", "")
    }

    var mode = Mode.Add


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_server)
        initView()

        intent?.getStringExtra("TOPIC_ID")?.also { id ->
            var topic = mainStore.state.topicState.topics?.filter { it.id == id }?.first() ?: Topic()
            if (topic.serverId == "") {
                mode = Mode.Add
                mode.topicId = id
                mode.serverId = UUID.randomUUID().toString()
            } else {
                mode = Mode.Edit
                mode.topicId = id
                mode.serverId = topic.serverId
            }
        }


        val action = ServerState.LoadServerAction()
        action.id = ""
        mainStore.dispatch(action)

        mainStore.subscribe(this){
            it.select { it.serverState }
        }
    }

    private var serverViewModel = AddServerAdapter.ServerViewModel()


    private fun initView() {

        recyclerView.layoutManager =  LinearLayoutManager(this@AddServerActivity)

    }

    private fun handleSelectServer() {
        val intent = Intent(this, ServerSelectionActivity::class.java)
        startActivity(intent)

    }

    private fun handleSaveServer() {

        val server = Server(
            mode.serverId,
            serverViewModel.name,
            serverViewModel.server,
            serverViewModel.user,
            serverViewModel.password,
            serverViewModel.port,
            serverViewModel.sslPort
        )


        when (mode) {

            Mode.Add -> {

                val addServerAction = ServerState.AddServerAction()
                addServerAction.server = server
                mainStore.dispatch(addServerAction)

                val updateTopicAction = TopicState.UpdateTopicAction()
                var topic = mainStore.state.topicState.topics?.filter { it.id == mode.topicId }?.first()

                topic?.serverId = server.id
                updateTopicAction.topic = topic
                mainStore.dispatch(updateTopicAction)
            }

            Mode.Edit -> {

                val updateServerAction = ServerState.UpdateServerAction()
                updateServerAction.server = server
                mainStore.dispatch(updateServerAction)
            }
        }

        finish()
    }

    private fun handleDeleteServer(information: String) {
        val deleteServerAction = ServerState.RemoveServerAction()
        deleteServerAction.id = mode.serverId
        mainStore.dispatch(deleteServerAction)

        val updateTopicAction = TopicState.UpdateTopicAction()
        var topic = mainStore.state.topicState.topics?.filter { it.id == mode.topicId }?.first()
        topic?.serverId = ""
        updateTopicAction.topic = topic
        mainStore.dispatch(updateTopicAction)

        finish()
    }

}
