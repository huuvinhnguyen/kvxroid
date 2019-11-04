package com.ving.kvxroid.ItemList.Detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.ving.kvxroid.R
import com.ving.kvxroid.Redux.AppState
import com.ving.kvxroid.Redux.ItemActionAdd
import com.ving.kvxroid.Redux.ItemNameActionLoad
import com.ving.kvxroid.Redux.mainStore
import com.ving.kvxroid.setOnSafeClickListener
import kotlinx.android.synthetic.main.activity_item_name.*
import kotlinx.android.synthetic.main.item_image_view_holder.view.*
import org.rekotlin.StoreSubscriber

class ItemNameActivity : AppCompatActivity(), StoreSubscriber<AppState> {

    override fun newState(state: AppState) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val counting = "${state.counter}"
        println(counting)
        Glide.with(imageView.context)  //2
            .load(state.itemNameViewModel?.imageUrl) //3
            .centerCrop() //4
            .placeholder(R.drawable.ic_image_place_holder) //5
            .error(R.drawable.ic_broken_image) //6
            .fallback(R.drawable.ic_no_image) //7
            .into(imageView)

    }

    private lateinit var viewModel: ItemNameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_name)
        mainStore.subscribe(this)
        initView()
        btnImage.setOnSafeClickListener {
            val intent = Intent(this, ItemImageActivity::class.java)
            startActivity(intent)
        }

        btnSave.setOnSafeClickListener {
            val action = ItemActionAdd()
            action.name = etName.text.toString()
            mainStore.dispatch(action)
            finish()
        }

    }

    private fun initView() {

        mainStore.subscribe(this)
        val action = ItemNameActionLoad()
        action.itemNameViewModel = ItemNameViewModel("avc", "https://i.ibb.co/F6kzXGj/61665260-810273342690754-5099592851554041856-n.jpg")
        mainStore.dispatch(action)


    }

}
