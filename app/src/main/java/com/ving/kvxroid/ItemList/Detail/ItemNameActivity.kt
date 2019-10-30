package com.ving.kvxroid.ItemList.Detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ving.kvxroid.R
import com.ving.kvxroid.Redux.AppState
import com.ving.kvxroid.Redux.ItemActionAdd
import com.ving.kvxroid.Redux.mainStore
import com.ving.kvxroid.setOnSafeClickListener
import kotlinx.android.synthetic.main.activity_item_name.*
import org.rekotlin.StoreSubscriber

class ItemNameActivity : AppCompatActivity(), StoreSubscriber<AppState> {

    override fun newState(state: AppState) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val counting = "${state.counter}"
        println(counting)

    }

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

    }

}
