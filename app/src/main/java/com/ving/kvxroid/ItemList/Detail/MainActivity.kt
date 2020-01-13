package com.ving.kvxroid.ItemList.Detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Detail.ItemDetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import com.ving.kvxroid.R
import com.ving.kvxroid.Redux.*
import org.rekotlin.StoreSubscriber
import org.rekotlinrouter.Route
import org.rekotlinrouter.SetRouteAction
import org.rekotlinrouter.SetRouteSpecificData


class MainActivity : AppCompatActivity(), StoreSubscriber<ItemState> {
    override fun newState(state: ItemState) {

        val itemListAdapter = ItemListGridRecyclerAdapter(state.items as ArrayList<AnyObject>).apply {
            onItemClick = ::handleItemClick
            onItemPlusClick = ::handlePlusClick

        }

        listRecyclerView.adapter = itemListAdapter

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()


        mainStore.dispatch(ItemState.ItemListAction())

        // subscribe to state changes
        mainStore.subscribe(this){
            it.select { it.itemState }
                .skipRepeats()
        }

        initRoute()


    }

    override fun onDestroy() {
        super.onDestroy()
        mainStore.unsubscribe(this)
    }

    private fun initRoute() {
        val routes = arrayListOf(itemActivityRoute)
        val action = SetRouteAction(route = routes)
        mainStore.dispatch(action)
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

    private fun handleItemClick(id: String) {

        val intent = Intent(this, ItemDetailActivity::class.java)
//        intent.putExtra("ITEM_ID", id)
//        startActivity(intent)

        val routes = arrayListOf(itemActivityRoute, itemDetailActivityRoute)
        val actionData =  SetRouteSpecificData(route = routes as Route, data = id)

        val action = SetRouteAction(route = routes)
        mainStore.dispatch(actionData)
        mainStore.dispatch(action)
    }

    private fun handlePlusClick(information: String) {
        println("Handle Plus click")
//        val intent = Intent(this, ItemNameActivity::class.java)
//        startActivity(intent)

        val routes = arrayListOf(itemActivityRoute, itemNameActivityRoute)
        val action = SetRouteAction(route = routes)
        mainStore.dispatch(action)


    }

}
