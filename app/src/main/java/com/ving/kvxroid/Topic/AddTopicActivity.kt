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
import com.ving.kvxroid.Redux.TopicState
import com.ving.kvxroid.Redux.mainStore
import kotlinx.android.synthetic.main.activity_item_topic.recyclerView
import java.util.*
import kotlin.collections.ArrayList

class AddTopicActivity : AppCompatActivity() {

    private lateinit var viewModel: ItemTopicViewModel

    private var topicViewModel = TopicViewModel()
    private var topicSwitchViewModel = TopicSwitchViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_topic)
//        btnConnect.setOnSafeClickListener {
//            val text = "Connect fails!"
//            val duration = Toast.LENGTH_SHORT
//
//            val toast = Toast.makeText(applicationContext, text, duration)
//            toast.setGravity(Gravity.CENTER_VERTICAL or Gravity.LEFT, 0, 0)
//
//            toast.show()
//
//            mainStore.dispatch(ConnectionActionAdd())
//
//
//        }
//
//        btnSelect.setOnSafeClickListener {
//            val intent = Intent(this, ServerSelectionActivity::class.java)
//            startActivity(intent)
//        }
//
//        btnTopic.setOnSafeClickListener {
//            val action = TopicState.AddTopicAction()
////            action.viewModel = viewModel
//            mainStore.dispatch(action)
//        }

        initView()

    }



    private fun initView() {

        val items: ArrayList<AnyObject> = ArrayList()

        topicViewModel = TopicViewModel("live", "switch")
        topicSwitchViewModel = TopicSwitchViewModel("")

        items.add(topicViewModel)
        items.add(topicSwitchViewModel)
        items.add(TopicQosViewModel())
        items.add(TopicSaveViewModel())

        val adapter = AddTopicAdapter(items as ArrayList<AnyObject>).apply {
            onSaveClick = ::handleSaveClick
            onSelectClick =::handleSelectClick
        }

        recyclerView.adapter = adapter

        adapter.setItems()

        recyclerView.layoutManager =  LinearLayoutManager(this@AddTopicActivity)

    }

    private fun handleSelectClick(information: String) {
        val intent = Intent(this, TopicTypeActivity::class.java)
        startActivity(intent)
    }

    private fun handleSaveClick(information: String) {
        val model = topicViewModel
        val topic = Topic(UUID.randomUUID().toString(), topicViewModel.name , topicViewModel.topic , "", "", "", topicViewModel.type, "")

        val action = TopicState.AddTopicAction()
        action.topic = topic
        mainStore.dispatch(action)
        finish()


    }
}
