package com.ving.kvxroid.Selection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Common.ResourceUtil
import com.ving.kvxroid.Detail.ItemDetailRecyclerAdapter
import com.ving.kvxroid.R
import com.ving.kvxroid.Redux.AppState
import com.ving.kvxroid.Redux.ConnectionActionLoad
import com.ving.kvxroid.Redux.ItemListStateLoad
import com.ving.kvxroid.Redux.mainStore
import kotlinx.android.synthetic.main.activity_item_detail.*

import io.realm.Realm
import io.realm.kotlin.createObject
import org.rekotlin.StoreSubscriber
import java.util.*
import kotlin.collections.ArrayList


class SelectionActivity : AppCompatActivity(), StoreSubscriber<AppState> {

    override fun newState(state: AppState) {

        recyclerView.layoutManager =  LinearLayoutManager(this@SelectionActivity)


        val itemListAdapter = SelectionAdapter(state.connectionList as ArrayList<AnyObject>).apply {

        }

        recyclerView.adapter = itemListAdapter

        print("new state SelectionActivity")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
        initView()
        mainStore.dispatch(ConnectionActionLoad())

        mainStore.subscribe(this)



    }

    private fun initView() {


        recyclerView.layoutManager =  LinearLayoutManager(this@SelectionActivity)


    }

}
