package com.ving.kvxroid.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_item_detail.*
import android.content.Intent
import android.util.Log
import com.ving.kvxroid.AnyObject
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
        items.add(ItemDetailHeaderViewModel("header 13"))

        val list = state.topics.map {
            ItemDetailAdapter.SwitchViewModel(it.id, it.name, it.value)
        }

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

        mainStore.dispatch(TopicState.LoadTopicsAction())

        mainStore.subscribe(this){
            it.select { it.topicState }
//                .skipRepeats()
        }

//        connect(this@ItemDetailActivity)
//        disconnectClient()

//        subscribe("test1")
//        receiveMessages()
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        mqttAndroidClient.unregisterResources()
//        mqttAndroidClient.close()
//    }

    private fun initView() {
        recyclerView.layoutManager =  LinearLayoutManager(this@ItemDetailActivity)

        adapter = ItemDetailAdapter(ArrayList()).apply {
            onItemClick = ::handleItemClick
            onItemEditClick = ::handleEditClick
            onItemPlusClick = ::handlePlusClick
            onItemTrashClick = ::handleTrashClick
            onInfoClick = ::handleInfoClick
            onTrashClick = ::handleTopicRemoveClick
            onSwitchClick = ::handleSwitchClick
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
        }


        val action = SetRouteAction(route = routes)
        mainStore.dispatch(action)

    }

    private fun handlePlusClick(information: String) {

        println("Plus Button")
        val intent = Intent(this, AddTopicActivity::class.java)
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

    private fun handleSwitchClick(information: String) {

    }

    override fun onDestroy() {
        super.onDestroy()
        mainStore.unsubscribe(this)
    }

    override fun onBackPressed() {

        val routes = arrayListOf(itemActivityRoute)
        val action = SetRouteAction(route = routes)
        mainStore.dispatch(action)
        super.onBackPressed()
    }

//    fun connect(applicationContext : Context) {
//        mqttAndroidClient = MqttAndroidClient ( applicationContext,"tcp://m24.cloudmqtt.com:14029","1234234" )
//        try {
//            val mqttConnectOptions = MqttConnectOptions()
//            mqttConnectOptions.isAutomaticReconnect = true
//            mqttConnectOptions.isCleanSession = false
//            mqttConnectOptions.userName = "plefajkt"
//            mqttConnectOptions.password = "i5DuqlA-kT2q".toCharArray()
//            mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1)
//
//
//            val token = mqttAndroidClient.connect(mqttConnectOptions)
//            token.actionCallback = object : IMqttActionListener {
//                override fun onSuccess(asyncActionToken: IMqttToken)                        {
//                    Log.i("Connection", "success ")
//                    //connectionStatus = true
//                    // Give your callback on connection established here
//                    subscribe("test1")
//                }
//                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
//                    //connectionStatus = false
//                    Log.i("Connection", "failure")
//                    // Give your callback on connection failure here
//                    exception.printStackTrace()
//                }
//            }
//        } catch (e: MqttException) {
//            // Give your callback on connection failure here
//            e.printStackTrace()
//        }
//    }


//    fun subscribe(topic: String) {
//        val qos = 2 // Mention your qos value
//        try {
//            mqttAndroidClient.subscribe(topic, qos, null, object : IMqttActionListener {
//                override fun onSuccess(asyncActionToken: IMqttToken) {
//                    // Give your callback on Subscription here
//                }
//                override fun onFailure(
//                    asyncActionToken: IMqttToken,
//                    exception: Throwable
//                ) {
//                    // Give your subscription failure callback here
//                }
//            })
//        } catch (e: MqttException) {
//            // Give your subscription failure callback here
//        }
//    }

//    fun unSubscribe(topic: String) {
//        try {
//            val unsubToken = mqttAndroidClient.unsubscribe(topic)
//            unsubToken.actionCallback = object : IMqttActionListener {
//                override fun onSuccess(asyncActionToken: IMqttToken) {
//                    // Give your callback on unsubscribing here
//                }
//                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
//                    // Give your callback on failure here
//                }
//            }
//        } catch (e: MqttException) {
//            // Give your callback on failure here
//        }
//    }

//    fun receiveMessages() {
//        mqttAndroidClient.setCallback(object : MqttCallback {
//            override fun connectionLost(cause: Throwable) {
//                //connectionStatus = false
//                // Give your callback on failure here
//            }
//            override fun messageArrived(topic: String, message: MqttMessage) {
//                try {
//                    val data = String(message.payload, charset("UTF-8"))
//                    // data is the desired received message
//                    // Give your callback on message received here
//                } catch (e: Exception) {
//                    // Give your callback on error here
//                }
//            }
//            override fun deliveryComplete(token: IMqttDeliveryToken) {
//                // Acknowledgement on delivery complete
//            }
//        })
//    }
//
//    fun publish(topic: String, data: String) {
//        val encodedPayload : ByteArray
//        try {
//            encodedPayload = data.toByteArray(charset("UTF-8"))
//            val message = MqttMessage(encodedPayload)
//            message.qos = 2
//            message.isRetained = false
//            mqttAndroidClient.publish(topic, message)
//        } catch (e: Exception) {
//            // Give Callback on error here
//        } catch (e: MqttException) {
//            // Give Callback on error here
//        }
//    }

//    fun disconnect() {
//        try {
//            val disconToken = mqttAndroidClient.disconnect()
//            disconToken.actionCallback = object : IMqttActionListener {
//                override fun onSuccess(asyncActionToken: IMqttToken) {
//                    //connectionStatus = false
//                    // Give Callback on disconnection here
//                }
//                override fun onFailure(
//                    asyncActionToken: IMqttToken,
//                    exception: Throwable
//                ) {
//                    // Give Callback on error here
//                }
//            }
//        } catch (e: MqttException) {
//            // Give Callback on error here
//        }
//    }
//
//    private fun disconnectClient() {
//        if (mqttAndroidClient != null) {
//            mqttAndroidClient.unregisterResources()
//            mqttAndroidClient.close()
//        }
//    }
}
