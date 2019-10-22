package com.ving.kvxroid.TopicDetailActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ving.kvxroid.R
import kotlinx.android.synthetic.main.activity_topic_detail.*


class TopicDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_detail)
        initView()
    }

    private fun initView() {

//        //This will for default android divider
//        recyclerViewMovies.addItemDecoration(GridItemDecoration(10, 2))
        recyclerView.layoutManager =  LinearLayoutManager(this@TopicDetailActivity)


        val itemListAdapter = TopicDetailAdapter(ArrayList()).apply {

        }

        recyclerView.adapter = itemListAdapter

//        movieListAdapter.setMovieList(generateDummyData())
        itemListAdapter.setItems()
    }
}
