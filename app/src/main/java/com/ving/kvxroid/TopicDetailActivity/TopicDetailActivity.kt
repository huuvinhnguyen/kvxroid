package com.ving.kvxroid.TopicDetailActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Models.Topic
import com.ving.kvxroid.R
import com.ving.kvxroid.Redux.ServerState
import com.ving.kvxroid.Redux.TopicState
import com.ving.kvxroid.Redux.mainStore
import com.ving.kvxroid.Topic.AddServerActivity
import com.ving.kvxroid.Topic.AddTopicActivity
import com.ving.kvxroid.extensions.empty
import kotlinx.android.synthetic.main.activity_topic_detail.*
import org.rekotlin.StoreSubscriber


class TopicDetailActivity : AppCompatActivity(), StoreSubscriber<Pair<TopicState, ServerState>> {

    override fun newState(state: Pair<TopicState, ServerState>) {
        val topic = state.first.topic ?: Topic()
        val server = state.second.server

        var items: ArrayList<AnyObject> = arrayListOf()

        items.add(TopicDetailAdapter.TopicDetailHeaderViewModel(topic.id, topic.name))

        if (topic.type == "gauge") {
            items.add(TopicDetailAdapter.GaugeViewModel(topic.id, name = topic.name, value = topic.value))

        }
        items.add(TopicDetailViewModel(topic.id, topic.topic, topic.value, topic.time, topic.qos, topic.type, topic.retain ))

        if (topic?.serverId == String.empty()) {

            items.add(TopicDetailAdapter.TopicDetailLoginViewModel(topic?.id ?: ""))
        } else {

            items.add(TopicDetailServerViewModel(server?.id ?: "",topic?.id ?: "",server?.name ?: "", server?.url ?:"",server?.user ?: "", server?.password ?: "", server?.port ?: "", server?.sslPort ?: ""))
        }


        items.add(TopicDetailAdapter.TopicDetailFooterViewModel(topic?.id ?: ""))
        adapter.setItems(items)

    }

    lateinit private var adapter: TopicDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_detail)
        initView()

        mainStore.subscribe(this) {
            it.select { Pair(it.topicState, it.serverState) }
        }

        intent?.getStringExtra("TOPIC_ID")?.also {
            val action = TopicState.LoadTopicAction()
            action.id = it
            mainStore.dispatch(action)
        }
    }

    private fun initView() {

        recyclerView.layoutManager = LinearLayoutManager(this@TopicDetailActivity)


        adapter = TopicDetailAdapter(ArrayList()).apply {
            onLoginClick = ::handleLoginClick
            onLogoutClick = :: handleLogoutClick
            onEditTopicClick = :: handleEditTopicClick
            onEditServerClick = :: handleEditServerClick
            onTrashClick = ::handleTrashClick
        }

        recyclerView.adapter = adapter

    }

    private fun handleLoginClick(information: String) {

        val intent = Intent(this, AddServerActivity::class.java)
        intent.putExtra("TOPIC_ID", information)

        startActivity(intent)
    }

    private fun handleLogoutClick(information: String) {

        intent?.getStringExtra("TOPIC_ID")?.also { id ->
            var topic = mainStore.state.topicState.topics?.filter { it.id == id}?.first()
            topic?.serverId = ""

            val action = TopicState.UpdateTopicAction()
            action.topic = topic
            mainStore.dispatch(action)
        }

    }

    private fun handleTrashClick(id: String) {
        val action = TopicState.RemoveTopicAction()
        action.id = id
        mainStore.dispatch(action)
        finish()
    }

    private fun handleEditTopicClick(id: String) {
        val intent = Intent(this, AddTopicActivity::class.java)
        intent.putExtra("TOPIC_ID", id)
        startActivity(intent)

    }

    private fun handleEditServerClick(id: String) {

        val intent = Intent(this, AddServerActivity::class.java)
        intent.putExtra("TOPIC_ID", id)

        startActivity(intent)

    }
}
