package com.ving.kvxroid.Topic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Detail.ItemTopicViewModel
import com.ving.kvxroid.Models.Topic
import com.ving.kvxroid.R
import com.ving.kvxroid.Redux.ItemState
import com.ving.kvxroid.Redux.ServerState
import com.ving.kvxroid.Redux.TopicState
import com.ving.kvxroid.Redux.mainStore
import kotlinx.android.synthetic.main.activity_item_topic.recyclerView
import org.rekotlin.StoreSubscriber
import java.util.*
import kotlin.collections.ArrayList

class AddTopicActivity : AppCompatActivity(), StoreSubscriber<TopicState> {

    override fun newState(state: TopicState) {

        topic = state.editableTopic ?: Topic()

        topic.let {
            val items: ArrayList<AnyObject> = ArrayList()

            topicViewModel = TopicViewModel(it.id, it.name, it.topic, it.type)
            topicSwitchViewModel = TopicSwitchViewModel("")

            items.add(topicViewModel)
            items.add(topicSwitchViewModel)
            items.add(TopicQosViewModel())
            items.add(TopicSaveViewModel())

            adapter.setItems(items)
        }
    }

    enum class Mode(var topicId: String) {
        Add(topicId = ""),
        Edit("")
    }

    var mode: Mode = Mode.Add
    private var topic = Topic()

    private var topicViewModel = TopicViewModel()
    private var topicSwitchViewModel = TopicSwitchViewModel()
    lateinit var adapter: AddTopicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_topic)
        initView()

        intent?.getStringExtra("TOPIC_ID")?.also { id ->
            mode = Mode.Edit
            mode.topicId = id
        }

        when(mode) {

            Mode.Add -> {
                var topic = Topic()

                topic.type = "switch"
                val action = TopicState.FetchEditableTopicAction()
                action.topic = topic
                mainStore.dispatch(action)

            }

            Mode.Edit -> {
//                val topic = mainStore.state.topicState.topic
                val action = TopicState.FetchEditableTopicAction()
                action.topic = mainStore.state.topicState.topic
                mainStore.dispatch(action)

            }
        }

        mainStore.subscribe(this){
            it.select { it.topicState }
        }
    }

    private fun initView() {

        recyclerView.layoutManager =  LinearLayoutManager(this@AddTopicActivity)
        adapter = AddTopicAdapter(ArrayList()).apply {
            onSaveClick = ::handleSaveClick
            onSelectClick =::handleSelectClick
        }

        recyclerView.adapter = adapter

    }

    private fun handleSelectClick(information: String) {
        val intent = Intent(this, TopicTypeActivity::class.java)
        startActivity(intent)
    }

    private fun handleSaveClick(information: String) {

        topic.name = topicViewModel.name
        topic.topic = topicViewModel.topic
        topic.type = topicViewModel.type

        when(mode) {

            Mode.Add -> {

                val topic = Topic(UUID.randomUUID().toString(), topic.name , topic.topic , "", "", topic.serverId, topic.type, "")
                val action = TopicState.AddTopicAction()
                action.topic = topic
                mainStore.dispatch(action)
            }

            Mode.Edit -> {

                val topic = Topic( mode.topicId, topic.name , topic.topic , topic.value, "", topic.serverId, topic.type, "")
                val action = TopicState.UpdateTopicAction()
                action.topic = topic
                mainStore.dispatch(action)

            }
        }

        finish()


    }
}
