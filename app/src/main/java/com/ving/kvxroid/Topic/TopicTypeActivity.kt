package com.ving.kvxroid.Topic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Models.Topic
import com.ving.kvxroid.R
import com.ving.kvxroid.Redux.TopicState
import com.ving.kvxroid.Redux.mainStore
import kotlinx.android.synthetic.main.activity_topic_type.*

class TopicTypeActivity : AppCompatActivity() {

    private lateinit var adapter: TopicTypeAdapter
    private val items = arrayListOf(
        TopicTypeAdapter.ItemViewModel("switch",true),
        TopicTypeAdapter.ItemViewModel("value",false),
        TopicTypeAdapter.ItemViewModel("gauge",false),
        TopicTypeAdapter.ItemViewModel("number",false)

    ) as ArrayList<AnyObject>

    private var topic = Topic()


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_save -> {

                var topic = mainStore.state.topicState.editableTopic

                val viewModel = items.filter { it as TopicTypeAdapter.ItemViewModel
                    it.isSelected == true
                }.first() as TopicTypeAdapter.ItemViewModel

                topic?.type = viewModel.title

                val action = TopicState.FetchEditableTopicAction()
                action.topic = topic
                mainStore.dispatch(action)


                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_type)
        initView()

    }

    private fun initView() {

        recyclerView.layoutManager =  LinearLayoutManager(this@TopicTypeActivity)

        adapter = TopicTypeAdapter(ArrayList()).apply {
            onItemClick = ::handleItemClick

        }

        recyclerView.adapter = adapter

        adapter.setItems(items)


        intent?.getStringExtra("TYPE_ID")?.also { id ->
            items.forEach { it as  TopicTypeAdapter.ItemViewModel
                it.isSelected = it.title == id

            }
        }


    }

    private fun handleItemClick(viewModel: TopicTypeAdapter.ItemViewModel) {

        items.forEach { it as TopicTypeAdapter.ItemViewModel
                it.isSelected = it.title == viewModel.title
        }

        adapter.setItems(items)

    }
}
