package com.ving.kvxroid.ItemList.Detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.ving.kvxroid.Models.Item
import com.ving.kvxroid.R
import com.ving.kvxroid.Redux.*
import com.ving.kvxroid.extensions.onChange
import com.ving.kvxroid.setOnSafeClickListener
import kotlinx.android.synthetic.main.activity_item_name.*
import org.rekotlin.StoreSubscriber
import org.rekotlinrouter.Route
import org.rekotlinrouter.SetRouteAction
import org.rekotlinrouter.SetRouteSpecificData
import java.util.*

class ItemNameActivity : AppCompatActivity(), StoreSubscriber<ItemState> {

    override fun newState(state: ItemState) {
        state.item?.let {
            item = it
            etName.hint = "Name"
            etName.setText(item.name)


        }
        state.item?.imageUrl.also {

            Glide.with(this)  //2
                .load(it) //3
                .centerCrop() //4
                .placeholder(R.drawable.ic_image_place_holder) //5
                .error(R.drawable.ic_broken_image) //6
                .fallback(R.drawable.ic_no_image) //7
                .into(imageView)

        }


    }

    enum class Mode {
        edit, addnew
    }

    lateinit var mode: Mode

    private lateinit var item: Item


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_name)
         initView()

        btnSave.setOnSafeClickListener {

            val itemId = intent?.getStringExtra("ITEM_ID") ?: ""
            if (itemId == "") {

                item.id = UUID.randomUUID().toString()

                val action = ItemState.ItemAddAction()
                action.item = item
                mainStore.dispatch(action)

                val routes = arrayListOf(itemActivityRoute, itemDetailActivityRoute)
                val actionData =  SetRouteSpecificData(route = routes as Route, data = item.id)
                mainStore.dispatch(actionData)

            } else {
                val action = ItemState.ItemUpdateAction()
                action.item = item
                mainStore.dispatch(action)

                val routes = arrayListOf(itemActivityRoute, itemDetailActivityRoute)
                val actionData =  SetRouteSpecificData(route = routes as Route, data = itemId)
                mainStore.dispatch(actionData)

            }


            val routes = arrayListOf(itemActivityRoute, itemDetailActivityRoute)
            val routeAction = SetRouteAction(route = routes)
            mainStore.dispatch(routeAction)
            finish()
        }

        imageView.setOnSafeClickListener {
            val intent = Intent(this, ItemImageActivity::class.java)
            startActivity(intent)
        }

        val itemId = intent?.getStringExtra("ITEM_ID") ?: ""
        val action = ItemState.ItemLoadAction()
        action.id = itemId
        mainStore.dispatch(action)



    }

    private fun initView() {

        etName.onChange {
            item.name = it
        }

        mainStore.subscribe(this){
            it.select { it.itemState }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mainStore.unsubscribe(this)

    }

    override fun onBackPressed() {
        val currentRoute: Route = mainStore.state.navigationState.route.clone() as Route
        if(currentRoute.last() == itemNameActivityRoute) {
            currentRoute.remove(itemNameActivityRoute)
        }
        val action = SetRouteAction(route = currentRoute)
        mainStore.dispatch(action)
        super.onBackPressed()
    }
}
