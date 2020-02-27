package com.ving.kvxroid.Topic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Models.Topic
import com.ving.kvxroid.R
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
            topicQosViewModel = TopicQosViewModel(it.qos)
            topicRetainViewModel = TopicRetainViewModel(it.retain)

            items.add(topicViewModel)
            if (it.type == "switch") {
                items.add(topicSwitchViewModel)
            }
            items.add(topicQosViewModel)
            items.add(topicRetainViewModel)
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
    private var topicQosViewModel = TopicQosViewModel()
    private var topicRetainViewModel = TopicRetainViewModel()
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
                action.topic = mainStore.state.topicState.topic?.copy()
                mainStore.dispatch(action)

            }
        }

        mainStore.subscribe(this){
            it.select { it.topicState }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainStore.unsubscribe(this)
        mainStore.state.topicState.editableTopic = null
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mainStore.unsubscribe(this)
        mainStore.state.topicState.editableTopic = null

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
        topic.name = topicViewModel.name
        topic.topic = topicViewModel.topic
        topic.type = topicViewModel.type
        topic.qos = topicQosViewModel.value
        topic.retain = topicRetainViewModel.value

        val intent = Intent(this, TopicTypeActivity::class.java)
        intent.putExtra("TYPE_ID", information)
        startActivity(intent)
    }

    private fun handleSaveClick(information: String) {

        topic.name = topicViewModel.name
        topic.topic = topicViewModel.topic
        topic.type = topicViewModel.type
        topic.qos = topicQosViewModel.value
        topic.retain = topicRetainViewModel.value

        when(mode) {

            Mode.Add -> {

                val itemId = intent.getStringExtra("ITEM_ID") ?: ""

                val topic = Topic(
                    UUID.randomUUID().toString(),
                    topic.name ,
                    topic.topic ,
                    topic.value, "",
                    topic.serverId,
                    topic.type,
                    topicQosViewModel.value,
                    topicRetainViewModel.value,
                    itemId)

                val action = TopicState.AddTopicAction()
                action.topic = topic
                mainStore.dispatch(action)
            }

            Mode.Edit -> {

                val topic = Topic( mode.topicId, topic.name , topic.topic , topic.value, "", topic.serverId, topic.type, topicQosViewModel.value, topicRetainViewModel.value)
                val action = TopicState.UpdateTopicAction()
                action.topic = topic
                mainStore.dispatch(action)

            }
        }

        finish()


    }
}
