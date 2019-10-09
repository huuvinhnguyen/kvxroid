package com.ving.kvxroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import com.ving.kvxroid.Redux.AppState
import com.ving.kvxroid.Redux.CounterActionIncrease
import com.ving.kvxroid.Redux.mainStore
import org.rekotlin.StoreSubscriber


class MainActivity : AppCompatActivity(), StoreSubscriber<AppState> {
    override fun newState(state: AppState) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val counting = "${state.counter}"
        println(counting)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

        mainStore.dispatch(CounterActionIncrease())

        // subscribe to state changes
        mainStore.subscribe(this)

    }

    private fun initView() {
        listRecyclerView.layoutManager = GridLayoutManager(this@MainActivity,2)

//        //This will for default android divider
//        recyclerViewMovies.addItemDecoration(GridItemDecoration(10, 2))

        val itemListAdapter = ItemListGridRecyclerAdapter().apply {
            onItemClick = ::handleItemClick
        }
        listRecyclerView.adapter = itemListAdapter

//        movieListAdapter.setMovieList(generateDummyData())
        itemListAdapter.setList()
    }

    private fun handleItemClick() {
        println("Hello")
        mainStore.dispatch(CounterActionIncrease())

//        val intent = Intent(this, ItemDetailActivity::class.java)
//        startActivity(intent)
    }

}
