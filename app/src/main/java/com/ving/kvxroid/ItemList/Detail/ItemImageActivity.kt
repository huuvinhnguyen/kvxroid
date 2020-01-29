package com.ving.kvxroid.ItemList.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Models.Image
import com.ving.kvxroid.R
import com.ving.kvxroid.Redux.*
import kotlinx.android.synthetic.main.activity_item_image.*
import org.rekotlin.StoreSubscriber

class ItemImageActivity : AppCompatActivity(), StoreSubscriber<ItemState> {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_save -> {

                val action = ItemState.UpdateItemImageAction()
                action.imageUrl = imageUrl
                mainStore.dispatch(action)
                finish()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun newState(state: ItemState) {
        var items = state.images.map {
            it as Image
            ItemImageAdapter.ViewModel(it.id,it.name, it.imageUrl, it.isSelected)
        } as ArrayList<AnyObject>

        adapter.setItems(items)


    }

    private lateinit var adapter: ItemImageAdapter
    private var imageUrl: String = ""

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

        adapter = ItemImageAdapter(ArrayList()).apply {
                        onItemClick = ::handleItemClick
        }
        recyclerView.adapter = adapter

    }

    private fun handleItemClick(viewModel: ItemImageAdapter.ViewModel) {
        val action = ItemState.SelectImageAction()
        action.id = viewModel.id
        mainStore.dispatch(action)
        imageUrl = viewModel.imageUrl


    }
}
