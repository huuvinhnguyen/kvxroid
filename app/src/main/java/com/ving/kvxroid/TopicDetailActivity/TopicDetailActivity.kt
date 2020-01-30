package com.ving.kvxroid.TopicDetailActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.R
import com.ving.kvxroid.Redux.TopicState
import com.ving.kvxroid.Redux.mainStore
import com.ving.kvxroid.Topic.AddServerActivity
import kotlinx.android.synthetic.main.activity_topic_detail.*
import org.rekotlin.StoreSubscriber


class TopicDetailActivity : AppCompatActivity(), StoreSubscriber<TopicState> {

    override fun newState(state: TopicState) {
        val topic = state.topic
        var items: ArrayList<AnyObject> = arrayListOf()
        items.add(TopicDetailAdapter.TopicDetailHeaderViewModel(topic?.name ?: ""))
        items.add(TopicDetailViewModel(topic?.id ?: "", topic?.topic?:"", topic?.value ?: "", topic?.time?: "", topic?.qos ?: "", ""))
        items.add(TopicDetailServerViewModel("Server 1 1"))
        items.add(TopicDetailAdapter.TopicDetailLoginViewModel(""))
        items.add(TopicDetailAdapter.TopicDetailFooterViewModel(topic?.id ?: ""))
        adapter.setItems(items)

    }

    lateinit private var adapter: TopicDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_detail)
        initView()

        mainStore.subscribe(this) {
            it.select { it.topicState }
//                .skipRepeats()
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
            onItemLoginClick = ::handleLoginClick
            onTrashClick = ::handleTrashClick


        }

        recyclerView.adapter = adapter

    }

    private fun handleLoginClick(information: String) {

        val intent = Intent(this, AddServerActivity::class.java)
        startActivity(intent)
    }

    private fun handleTrashClick(id: String) {
        val action = TopicState.RemoveTopicAction()
        action.id = id
        mainStore.dispatch(action)
        finish()
    }
}
