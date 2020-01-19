package com.ving.kvxroid.TopicDetailActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ving.kvxroid.R
import com.ving.kvxroid.Topic.AddServerActivity
import kotlinx.android.synthetic.main.activity_topic_detail.*


class TopicDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_detail)
        initView()
    }

    private fun initView() {

        recyclerView.layoutManager =  LinearLayoutManager(this@TopicDetailActivity)


        val itemListAdapter = TopicDetailAdapter(ArrayList()).apply {
            onItemLoginClick = ::handleLoginClick


        }

        recyclerView.adapter = itemListAdapter

//        movieListAdapter.setMovieList(generateDummyData())
        itemListAdapter.setItems()
    }

    private fun handleLoginClick(information: String) {

        val intent = Intent(this, AddServerActivity::class.java)
        startActivity(intent)
    }
}
