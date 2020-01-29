package com.ving.kvxroid.Topic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Models.Server
import com.ving.kvxroid.R
import com.ving.kvxroid.Redux.ServerState
import com.ving.kvxroid.Redux.mainStore
import com.ving.kvxroid.Selection.ServerSelectionActivity

import kotlinx.android.synthetic.main.activity_add_server.recyclerView
import java.util.*
import kotlin.collections.ArrayList

class AddServerActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_server)

        initView()

    }

    private var serverViewModel = AddServerAdapter.ServerViewModel()


    private fun initView() {

        val items: ArrayList<AnyObject> = ArrayList()

        items.add(serverViewModel)
        items.add(AddServerAdapter.ServerFooterViewModel(""))

        val adapter = AddServerAdapter(items as ArrayList<AnyObject>).apply {
            onSaveClick = ::handleSaveServer
            onSelectClick = ::handleSelectServer

        }

        recyclerView.adapter = adapter

        adapter.setItems()

        recyclerView.layoutManager =  LinearLayoutManager(this@AddServerActivity)

    }

    private fun handleSelectServer() {
        val intent = Intent(this, ServerSelectionActivity::class.java)
        startActivity(intent)

    }

    private fun handleSaveServer() {
        val action = ServerState.AddServerAction()
        action.server = Server(
            UUID.randomUUID().toString(),
            serverViewModel.name,
            serverViewModel.server,
            serverViewModel.user,
            serverViewModel.password,
            serverViewModel.port,
            serverViewModel.sslPort
        )

        mainStore.dispatch(action)
        finish()
    }

}
