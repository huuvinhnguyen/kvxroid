package com.ving.kvxroid.Selection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ving.kvxroid.ItemList.Detail.ItemListGridRecyclerAdapter
import com.ving.kvxroid.R
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.activity_main.*

class SelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
        initView()
    }

    private fun initView() {

//        //This will for default android divider
//        recyclerViewMovies.addItemDecoration(GridItemDecoration(10, 2))
        recyclerView.layoutManager =  LinearLayoutManager(this@SelectionActivity)


        val itemListAdapter = SelectionAdapter(ArrayList()).apply {

        }
        recyclerView.adapter = itemListAdapter

//        movieListAdapter.setMovieList(generateDummyData())
        itemListAdapter.setItems()
    }

}
