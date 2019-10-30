package com.ving.kvxroid.ItemList.Detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Detail.ItemDetailActivity
import com.ving.kvxroid.Detail.ItemDetailPlusViewModel
import kotlinx.android.synthetic.main.activity_main.*
import com.ving.kvxroid.R
import com.ving.kvxroid.Redux.AppState
import com.ving.kvxroid.Redux.CounterActionIncrease
import com.ving.kvxroid.Redux.ItemActionLoad
import com.ving.kvxroid.Redux.mainStore
import org.rekotlin.StoreSubscriber


class MainActivity : AppCompatActivity(), StoreSubscriber<AppState> {
    override fun newState(state: AppState) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val counting = "${state.counter}"
        println(counting)


        val itemListAdapter = ItemListGridRecyclerAdapter(state.itemList as ArrayList<AnyObject>).apply {
            onItemClick = ::handleItemClick
            onItemPlusClick = ::handlePlusClick

        }

        listRecyclerView.adapter = itemListAdapter

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

//        mainStore.dispatch(CounterActionIncrease())

        mainStore.dispatch(ItemActionLoad())

        // subscribe to state changes
        mainStore.subscribe(this)

    }

    private fun initView() {
        listRecyclerView.layoutManager = GridLayoutManager(this@MainActivity,2)

//        //This will for default android divider
//        recyclerViewMovies.addItemDecoration(GridItemDecoration(10, 2))


//        val items: ArrayList<AnyObject> = ArrayList()
//        items.add(ItemViewModel("bye bye 1"))
//        items.add(ItemViewModel("hello helo 2"))
//        items.add(ItemDetailPlusViewModel())
//        val itemListAdapter = ItemListGridRecyclerAdapter(items).apply {
//            onItemClick = ::handleItemClick
//            onItemPlusClick = ::handlePlusClick
//
//        }
//
//        listRecyclerView.adapter = itemListAdapter

    }

    private fun handleItemClick() {
        println("Hello")
        mainStore.dispatch(CounterActionIncrease())

        val intent = Intent(this, ItemDetailActivity::class.java)
        startActivity(intent)
    }

    private fun handlePlusClick(information: String) {
        println("Handle Plus click")
        val intent = Intent(this, ItemNameActivity::class.java)
        startActivity(intent)
    }

}
