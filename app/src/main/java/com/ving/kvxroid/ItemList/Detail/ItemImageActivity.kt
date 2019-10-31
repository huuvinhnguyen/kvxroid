package com.ving.kvxroid.ItemList.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Detail.ItemDetailPlusViewModel
import com.ving.kvxroid.R
import com.ving.kvxroid.Redux.AppState
import com.ving.kvxroid.Redux.ItemImageActionLoad
import com.ving.kvxroid.Redux.mainStore
import kotlinx.android.synthetic.main.activity_item_image.*
import kotlinx.android.synthetic.main.activity_main.*
import org.rekotlin.StoreSubscriber

class ItemImageActivity : AppCompatActivity(), StoreSubscriber<AppState> {

    override fun newState(state: AppState) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val counting = "${state.counter}"
        println(counting)

        recyclerView.layoutManager = GridLayoutManager(this@ItemImageActivity,2)

//        val items: ArrayList<AnyObject> = ArrayList()
//        items.add(ItemImageViewModel("1", "abcde", "https://i.ibb.co/F6kzXGj/61665260-810273342690754-5099592851554041856-n.jpg"))
//        items.add(ItemImageViewModel("1", "aaaaa", "https://i.ibb.co/F6kzXGj/61665260-810273342690754-5099592851554041856-n.jpg"))
        val itemListAdapter = ItemImageAdapter(state.itemImageList as ArrayList<AnyObject>).apply {
            //            onItemClick = ::handleItemClick
//            onItemPlusClick = ::handlePlusClick

        }

        recyclerView.adapter = itemListAdapter


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_image)
        initView()


        mainStore.dispatch(ItemImageActionLoad())

        mainStore.subscribe(this)


    }

    private fun initView() {
//        recyclerView.layoutManager = GridLayoutManager(this@ItemImageActivity,2)
//
//        val items: ArrayList<AnyObject> = ArrayList()
//        items.add(ItemImageViewModel("1", "abcde", "https://i.ibb.co/F6kzXGj/61665260-810273342690754-5099592851554041856-n.jpg"))
//        items.add(ItemImageViewModel("1", "aaaaa", "https://i.ibb.co/F6kzXGj/61665260-810273342690754-5099592851554041856-n.jpg"))
//        val itemListAdapter = ItemImageAdapter(items).apply {
//            //            onItemClick = ::handleItemClick
////            onItemPlusClick = ::handlePlusClick
//
//        }
//
//        recyclerView.adapter = itemListAdapter
    }
}
