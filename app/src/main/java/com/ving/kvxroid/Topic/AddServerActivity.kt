package com.ving.kvxroid.Topic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.R
import com.ving.kvxroid.Selection.SelectionActivity

import kotlinx.android.synthetic.main.activity_add_server.recyclerView
import kotlinx.android.synthetic.main.activity_item_topic.*

class AddServerActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_server)

        initView()

    }

    private fun initView() {

        val items: ArrayList<AnyObject> = ArrayList()

        items.add(AddServerAdapter.ServerViewModel("4444"))

        val adapter = AddServerAdapter(items as ArrayList<AnyObject>).apply {
            onSaveClick = ::handleSaveServer
            onSelectClick = ::handleSelectServer

        }

        recyclerView.adapter = adapter

        adapter.setItems()

        recyclerView.layoutManager =  LinearLayoutManager(this@AddServerActivity)

    }

    private fun handleSelectServer() {
        val intent = Intent(this, SelectionActivity::class.java)
        startActivity(intent)

    }

    private fun handleSaveServer() {
        finish()

    }

}
