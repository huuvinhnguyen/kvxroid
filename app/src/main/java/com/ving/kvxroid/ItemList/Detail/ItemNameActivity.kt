package com.ving.kvxroid.ItemList.Detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.ving.kvxroid.R
import com.ving.kvxroid.Redux.*
import com.ving.kvxroid.extensions.onChange
import com.ving.kvxroid.setOnSafeClickListener
import kotlinx.android.synthetic.main.activity_item_name.*
import org.rekotlin.StoreSubscriber
import org.rekotlinrouter.Route
import org.rekotlinrouter.SetRouteAction

class ItemNameActivity : AppCompatActivity(), StoreSubscriber<ItemState> {

    override fun newState(state: ItemState) {
        state.item?.imageUrl.also {

            Glide.with(this)  //2
                .load(it) //3
                .centerCrop() //4
                .placeholder(R.drawable.ic_image_place_holder) //5
                .error(R.drawable.ic_broken_image) //6
                .fallback(R.drawable.ic_no_image) //7
                .into(imageView)

        }

        etName.hint = state.item?.name ?: "Name"

    }

    enum class Mode {
        edit, addnew
    }

    lateinit var mode: Mode

    private lateinit var viewModel: ItemNameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_name)
         initView()
        btnImage.setOnSafeClickListener {
            val intent = Intent(this, ItemImageActivity::class.java)
            startActivity(intent)
        }

        btnSave.setOnSafeClickListener {
            val action = ItemState.ItemAddAction()
            action.name = etName.text.toString()
            mainStore.dispatch(action)


            val routes = arrayListOf(itemActivityRoute, itemDetailActivityRoute)
            val routeAction = SetRouteAction(route = routes)
            mainStore.dispatch(routeAction)
            finish()
        }

        imageView.setOnSafeClickListener {
            val intent = Intent(this, ItemImageActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initView() {

        etName.onChange {
            print(it.toString())
        }

        mainStore.subscribe(this){
            it.select { it.itemState }
                .skipRepeats()
        }

//        val action = ItemNameActionLoad()
//        action.itemNameViewModel = ItemNameViewModel("avc", "https://i.ibb.co/F6kzXGj/61665260-810273342690754-5099592851554041856-n.jpg")
//        mainStore.dispatch(action)
        mainStore.dispatch(ItemState.ItemLoadAction())


    }

    private fun updateItem() {

    }

    private fun addItem() {

        val action = ItemState.ItemAddAction()
        action.name = etName.text.toString()
        mainStore.dispatch(action)


        val routes = arrayListOf(itemActivityRoute, itemDetailActivityRoute)
        val routeAction = SetRouteAction(route = routes)
        mainStore.dispatch(routeAction)
        finish()

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
