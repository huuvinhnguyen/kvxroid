package com.ving.kvxroid.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ving.kvxroid.R
import kotlinx.android.synthetic.main.activity_item_detail.*

class ItemDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        initView()
    }

    private fun initView() {
//        recyclerView.layoutManager = GridLayoutManager(this@ItemDetailActivity,2)
        recyclerView.layoutManager =  LinearLayoutManager(this@ItemDetailActivity)


//        //This will for default android divider
//        recyclerViewMovies.addItemDecoration(GridItemDecoration(10, 2))

        val itemDetailAdapter = ItemDetailRecyclerAdapter().apply {
            onItemClick = ::handleItemClick
        }
        recyclerView.adapter = itemDetailAdapter

//        movieListAdapter.setMovieList(generateDummyData())
    }

    private fun handleItemClick() {
        println("Hello Detail")

    }
}
