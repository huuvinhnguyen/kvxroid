package com.ving.kvxroid.Topic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Models.Server
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

        val adapter = AddServerAdapter(ArrayList()).apply {
            onSaveClick = ::handleSaveServer
            onSelectClick = ::handleSelectServer

        }

        recyclerView.adapter = adapter

        adapter.setItems(items)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_server)
        initView()

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
            UUID.randomUUID().toString(),
            serverViewModel.name,
            serverViewModel.server,
            serverViewModel.user,
            serverViewModel.password,
            serverViewModel.port,
            serverViewModel.sslPort
        )

        intent?.getStringExtra("TOPIC_ID")?.also { id ->

            var topic = mainStore.state.topicState.topics.filter { it.id == id}.first()

            val action1 = TopicState.UpdateTopicAction()
            topic.serverId = server?.id
            action1.topic = topic
            mainStore.dispatch(action1)

        }


        val action2 = ServerState.AddServerAction()
        action2.server = server
        mainStore.dispatch(action2)

        finish()
    }

}
