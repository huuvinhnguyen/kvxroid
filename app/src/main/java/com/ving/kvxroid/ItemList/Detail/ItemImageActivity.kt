package com.ving.kvxroid.ItemList.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Detail.ItemDetailPlusViewModel
import com.ving.kvxroid.R
import com.ving.kvxroid.Redux.AppState
import com.ving.kvxroid.Redux.ItemImageActionFetch
import com.ving.kvxroid.Redux.ItemImageActionLoad
import com.ving.kvxroid.Redux.mainStore
import kotlinx.android.synthetic.main.activity_item_image.*
import kotlinx.android.synthetic.main.activity_main.*
import org.rekotlin.StoreSubscriber

class ItemImageActivity : AppCompatActivity(), StoreSubscriber<AppState> {

    override fun newState(state: AppState) {
        val itemListAdapter = ItemImageAdapter(state.itemImageList).apply {}
        recyclerView.adapter = itemListAdapter

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_image)
        initView()
        mainStore.subscribe(this)
        mainStore.dispatch(ItemImageActionLoad())
    }

    private fun initView() {

        recyclerView.layoutManager = GridLayoutManager(this@ItemImageActivity,2)

    }
}
