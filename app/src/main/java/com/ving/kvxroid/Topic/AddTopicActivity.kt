package com.ving.kvxroid.Topic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Detail.ItemTopicViewModel
import com.ving.kvxroid.R
import com.ving.kvxroid.Redux.*
import com.ving.kvxroid.Selection.SelectionActivity
import com.ving.kvxroid.setOnSafeClickListener
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.activity_item_topic.*
import kotlinx.android.synthetic.main.activity_item_topic.recyclerView

class AddTopicActivity : AppCompatActivity() {

    private lateinit var viewModel: ItemTopicViewModel

    private var topicViewModel = TopicViewModel()

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
//            val intent = Intent(this, SelectionActivity::class.java)
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

        items.add(topicViewModel)
        items.add(TopicSwitchViewModel())
        items.add(TopicQosViewModel())
        items.add(TopicSaveViewModel())

        val itemDetailAdapter = AddTopicAdapter(items as ArrayList<AnyObject>).apply {
            onSaveClick = ::handleSaveClick
            onSelectClick =::handleSelectClick
        }

        recyclerView.adapter = itemDetailAdapter

        itemDetailAdapter.setItems()

        recyclerView.layoutManager =  LinearLayoutManager(this@AddTopicActivity)

    }

    private fun handleSelectClick(information: String) {
        val intent = Intent(this, TopicTypeActivity::class.java)
        startActivity(intent)
    }

    private fun handleSaveClick(information: String) {
        val model = topicViewModel
        print(model)
        print(information)
        finish()


    }
}
