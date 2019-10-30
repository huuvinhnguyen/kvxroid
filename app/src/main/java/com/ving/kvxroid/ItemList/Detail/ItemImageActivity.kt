package com.ving.kvxroid.ItemList.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Detail.ItemDetailPlusViewModel
import com.ving.kvxroid.R
import kotlinx.android.synthetic.main.activity_item_image.*
import kotlinx.android.synthetic.main.activity_main.*

class ItemImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_image)
        initView()
    }

    private fun initView() {
        recyclerView.layoutManager = GridLayoutManager(this@ItemImageActivity,2)

        val items: ArrayList<AnyObject> = ArrayList()
        items.add(ItemViewModel("bye bye 1"))
        items.add(ItemViewModel("hello helo 2"))
        val itemListAdapter = ItemImageAdapter(items).apply {
//            onItemClick = ::handleItemClick
//            onItemPlusClick = ::handlePlusClick

        }

        recyclerView.adapter = itemListAdapter

    }
}
