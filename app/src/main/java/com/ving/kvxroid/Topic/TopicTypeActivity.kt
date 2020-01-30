package com.ving.kvxroid.Topic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.R
import kotlinx.android.synthetic.main.activity_topic_type.*

class TopicTypeActivity : AppCompatActivity() {

    private lateinit var adapter: TopicTypeAdapter
    private val items = arrayListOf(
        TopicTypeAdapter.ItemViewModel("switch",false),
        TopicTypeAdapter.ItemViewModel("value",false),
        TopicTypeAdapter.ItemViewModel("humidity",false)
    ) as ArrayList<AnyObject>

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_save -> {


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

    }

    private fun handleItemClick(viewModel: TopicTypeAdapter.ItemViewModel) {

        items.forEach { it as TopicTypeAdapter.ItemViewModel
                it.isSelected = it.title == viewModel.title
        }

        adapter.setItems(items)

    }
}
