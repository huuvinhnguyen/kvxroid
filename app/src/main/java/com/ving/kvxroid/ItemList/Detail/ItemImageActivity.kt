package com.ving.kvxroid.ItemList.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Models.Image
import com.ving.kvxroid.Models.Item
import com.ving.kvxroid.R
import com.ving.kvxroid.Redux.*
import kotlinx.android.synthetic.main.activity_item_image.*
import org.rekotlin.StoreSubscriber

class ItemImageActivity : AppCompatActivity(), StoreSubscriber<ItemState> {

    override fun newState(state: ItemState) {
        var items = state.images.map {
            it as Image
            ItemImageAdapter.ViewModel(it.id,it.name, it.imageUrl, it.isSelected)
        } as ArrayList<AnyObject>
        val itemListAdapter = ItemImageAdapter(items).apply {
            onItemClick = ::handleItemClick
        }
        recyclerView.adapter = itemListAdapter

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_image)
        initView()
        // subscribe to state changes
        mainStore.subscribe(this){
            it.select { it.itemState }
//                .skipRepeats()
        }
        mainStore.dispatch(ItemState.LoadImagesAction())
    }

    private fun initView() {

        recyclerView.layoutManager = GridLayoutManager(this@ItemImageActivity,2)

    }

    private fun handleItemClick(viewModel: ItemImageAdapter.ViewModel) {
//        val action = ItemImageActionSelect()
        val action = ItemState.SelectImageAction()
        action.id = viewModel.id
        mainStore.dispatch(action)


        val action2 = ItemLoadAction()
        val itemRef = Item("1", "hello helli", viewModel.imageUrl)

        action2.itemViewModel = itemRef
        mainStore.dispatch(action2)

    }
}
