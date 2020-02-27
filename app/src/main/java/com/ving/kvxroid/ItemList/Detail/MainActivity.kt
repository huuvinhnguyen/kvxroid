package com.ving.kvxroid.ItemList.Detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Detail.ItemDetailActivity
import com.ving.kvxroid.Detail.ItemDetailPlusViewModel
import com.ving.kvxroid.Models.Item
import kotlinx.android.synthetic.main.activity_main.*
import com.ving.kvxroid.R
import com.ving.kvxroid.Redux.*
import org.rekotlin.StoreSubscriber
import org.rekotlinrouter.Route
import org.rekotlinrouter.SetRouteAction
import org.rekotlinrouter.SetRouteSpecificData


class MainActivity : AppCompatActivity(), StoreSubscriber<ItemState> {

    override fun newState(state: ItemState) {

        var items: ArrayList<AnyObject> = arrayListOf()

        val list = state.items.map { it as Item
            ItemListAdapter.ItemViewModel(it.id, it.name, it.imageUrl) }
        items.add(ItemDetailPlusViewModel())

        items.addAll(list)

        val itemListAdapter = ItemListAdapter(items as ArrayList<AnyObject>).apply {
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

    }

    private fun handleItemClick(id: String) {


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
