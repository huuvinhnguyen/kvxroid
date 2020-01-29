package com.ving.kvxroid.Selection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.R
import com.ving.kvxroid.Redux.*
import kotlinx.android.synthetic.main.activity_item_detail.*

import org.rekotlin.StoreSubscriber
import kotlin.collections.ArrayList


class ServerSelectionActivity : AppCompatActivity(), StoreSubscriber<ServerState> {

    override fun newState(state: ServerState) {

        val list = state.servers.map {
            ServerSelectionViewModel(it.name, false)
        }

        val adapter = ServerSelectionAdapter(list as ArrayList<AnyObject>).apply {
            onItemClick = ::handleItemClick
        }

        recyclerView.adapter = adapter

        adapter.setItems()

    }

    private lateinit var adapter: ServerSelectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server_selection)
        initView()
        mainStore.dispatch(ServerState.LoadServersAction())

        mainStore.subscribe(this){
            it.select { it.serverState }
        }


    }

    private fun initView() {

        recyclerView.layoutManager =  LinearLayoutManager(this@ServerSelectionActivity)

    }

    private fun handleItemClick(viewModel: ServerSelectionViewModel) {

//        items.forEach { it as TopicTypeAdapter.ItemViewModel
//            it.isSelected = it.title == viewModel.title
//        }
//
//        adapter.setItems(items)

    }
}
