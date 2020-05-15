package com.ving.kvxroid.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_item_detail.*
import android.content.Intent
import android.util.Log
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Models.Item
import com.ving.kvxroid.Redux.*
import com.ving.kvxroid.Topic.AddTopicActivity
import com.ving.kvxroid.TopicDetailActivity.TopicDetailActivity
import org.rekotlin.StoreSubscriber
import org.rekotlinrouter.Route
import org.rekotlinrouter.SetRouteAction
import org.rekotlinrouter.SetRouteSpecificData


class ItemDetailActivity : AppCompatActivity(), StoreSubscriber<TopicState> {

    override fun newState(state: TopicState) {

        val items: ArrayList<AnyObject> = ArrayList()

        val id = intent?.getStringExtra("ITEM_ID") ?: ""

        val itemName = mainStore.state.itemState.items.find { it.id == id }?.name ?: ""

        items.add(ItemDetailHeaderViewModel(itemName))



        val list = state.topics?.map {
            when(it.type) {
                "switch" -> ItemDetailAdapter.SwitchViewModel(it.id, it.name, it.value, it.time)
                "value" -> ItemDetailAdapter.ValueViewModel(it.id, it.name, it.value, it.time)
                "number" -> ItemDetailAdapter.NumberViewModel(it.id, it.name, it.value, it.time)
                "gauge" -> ItemDetailAdapter.GaugeViewModel(it.id, it.name, it.value)
                else -> throw IllegalArgumentException("Invalid type")
            }
        } ?: emptyList()

        Log.i("#new State", items.size.toString())


        items.addAll(list)

        adapter.setItems(items)

    }

//    private lateinit var mqttAndroidClient:
    private lateinit var adapter: ItemDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.ving.kvxroid.R.layout.activity_item_detail)
        initView()

        val id = intent?.getStringExtra("ITEM_ID") ?: ""
        val action = TopicState.LoadTopicsAction()
        action.itemId = id

        mainStore.dispatch(action)
        mainStore.subscribe(this){
            it.select { it.topicState }
//                .skipRepeats()
        }
    }


    private fun initView() {
        recyclerView.layoutManager =  LinearLayoutManager(this@ItemDetailActivity)

        adapter = ItemDetailAdapter(ArrayList()).apply {
            onItemClick = ::handleItemClick
            onItemEditClick = ::handleEditClick
            onItemPlusClick = ::handlePlusClick
            onItemTrashClick = ::handleTrashClick
            onInfoClick = ::handleInfoClick
            onTrashClick = ::handleTopicRemoveClick
            onSwitchClick = ::handlePublishClick
            onSendClick = ::handlePublishClick
        }

        recyclerView.adapter = adapter
    }

    private fun handleItemClick() {
        println("Hello Detail")

    }

    private fun handleEditClick(information: String) {

        println("Edit Button")

        val routes = arrayListOf(itemActivityRoute, itemDetailActivityRoute, itemNameActivityRoute)

        intent?.getStringExtra("ITEM_ID")?.also {
            val actionData =  SetRouteSpecificData(route = routes as Route, data = it)
            mainStore.dispatch(actionData)


            val action = SetRouteAction(route = routes)
            mainStore.dispatch(action)

        }
    }

    private fun handlePlusClick(information: String) {

        println("Plus Button")
        val itemId = intent?.getStringExtra("ITEM_ID") ?: ""
        val intent = Intent(this, AddTopicActivity::class.java)
        intent.putExtra("ITEM_ID", itemId)
        startActivity(intent)

    }

    private fun handleTrashClick(information: String) {

        intent?.getStringExtra("ITEM_ID")?.also {
            val action = ItemState.ItemRemoveAction()
            action.id = it

            mainStore.dispatch(action)

        }

        val routes = arrayListOf(itemActivityRoute)
        val routeAction = SetRouteAction(route = routes)
        mainStore.dispatch(routeAction)

        finish()

    }

    private fun handleTopicRemoveClick(information: String) {

        val action = TopicState.RemoveTopicAction()
        action.id = information
        mainStore.dispatch(action)

    }

    private fun handleInfoClick(information: String) {

        println("Info Button")
        val intent = Intent(this, TopicDetailActivity::class.java)
        intent.putExtra("TOPIC_ID", information)

        startActivity(intent)

    }

    private fun handlePublishClick(topicId: String, message: String) {
        val action = TopicState.PublishAction()
        action.topicId = topicId
        action.message = message
        mainStore.dispatch(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainStore.unsubscribe(this)
        mainStore.state.topicState.tasks?.clear()
        mainStore.state.topicState.topics =  arrayListOf()

    }

    override fun onBackPressed() {

        val routes = arrayListOf(itemActivityRoute)
        val action = SetRouteAction(route = routes)
        mainStore.dispatch(action)
        super.onBackPressed()
    }

}
