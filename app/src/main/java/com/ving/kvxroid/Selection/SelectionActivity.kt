package com.ving.kvxroid.Selection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ving.kvxroid.Common.ResourceUtil
import com.ving.kvxroid.R
import kotlinx.android.synthetic.main.activity_item_detail.*

import io.realm.Realm
import io.realm.kotlin.createObject
import java.util.*
import kotlin.collections.ArrayList


class SelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
        initView()
    }

    private fun initView() {


        recyclerView.layoutManager =  LinearLayoutManager(this@SelectionActivity)


        val itemListAdapter = SelectionAdapter(ArrayList()).apply {

        }

        recyclerView.adapter = itemListAdapter


        itemListAdapter.setItems()
    }

}
