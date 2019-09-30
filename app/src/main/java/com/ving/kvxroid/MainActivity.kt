package com.ving.kvxroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        listRecyclerView.layoutManager = GridLayoutManager(this@MainActivity,2)

//        //This will for default android divider
//        recyclerViewMovies.addItemDecoration(GridItemDecoration(10, 2))

        val itemListAdapter = ItemListGridRecyclerAdapter()
        listRecyclerView.adapter = itemListAdapter
//        movieListAdapter.setMovieList(generateDummyData())
        itemListAdapter.setList()
    }
}
