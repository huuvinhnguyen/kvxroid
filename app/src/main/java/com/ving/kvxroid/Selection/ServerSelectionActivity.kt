package com.ving.kvxroid.Selection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.R
import com.ving.kvxroid.Redux.*
import kotlinx.android.synthetic.main.activity_item_detail.*

import org.rekotlin.StoreSubscriber
import kotlin.collections.ArrayList


class ServerSelectionActivity : AppCompatActivity(), StoreSubscriber<ServerState> {

    override fun newState(state: ServerState) {

       items = state.servers.map {
            ServerSelectionViewModel( it.id, it.name, false)
        } as ArrayList<AnyObject>

        adapter = ServerSelectionAdapter(ArrayList()).apply {
            onItemClick = ::handleItemClick
        }

        recyclerView.adapter = adapter

        adapter.setItems(items)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_save -> {

                val items = items as ArrayList<ServerSelectionViewModel>
                val item = items.filter {
                    it.isSelected
                }.first()


//                val id = items.filter {
//                    it as ServerSelectionViewModel
//                    return it.isSelected
//                }.map {
//                    it as ServerSelectionViewModel
//                    it.id
//                }.first()

                val action = ServerState.LoadServerAction()
                action.id = item.id
                mainStore.dispatch(action)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private lateinit var adapter: ServerSelectionAdapter
    private var items: ArrayList<AnyObject> = ArrayList()

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


        items.forEach { it as ServerSelectionViewModel
            it.isSelected = it.id == viewModel.id
        }

        adapter.setItems(items)

    }
}
