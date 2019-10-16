package com.ving.kvxroid.Detail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.ving.kvxroid.R
import kotlinx.android.synthetic.main.activity_item_detail.*
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import android.R.attr.password
import android.content.Intent
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Redux.AppState
import com.ving.kvxroid.Redux.CounterActionIncrease
import com.ving.kvxroid.Redux.ItemListStateLoad
import com.ving.kvxroid.Redux.mainStore
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.rekotlin.StoreSubscriber


class ItemDetailActivity : AppCompatActivity(), StoreSubscriber<AppState> {

    override fun newState(state: AppState) {

        val itemDetailAdapter = ItemDetailRecyclerAdapter(state.itemDetailList as ArrayList<AnyObject>).apply {
            onItemClick = ::handleItemClick
            onItemPlusClick = ::handlePlusClick
        }
        recyclerView.adapter = itemDetailAdapter

        itemDetailAdapter.setItems()

//        movieListAdapter.setMovieList(generateDummyData())


    }

    private lateinit var mqttAndroidClient: MqttAndroidClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.ving.kvxroid.R.layout.activity_item_detail)
        initView()

        mainStore.dispatch(ItemListStateLoad())

        // subscribe to state changes
        mainStore.subscribe(this)

        connect(this@ItemDetailActivity)
//        disconnectClient()

//        subscribe("test1")
        receiveMessages()
    }

    override fun onDestroy() {
        super.onDestroy()
        mqttAndroidClient.unregisterResources()
        mqttAndroidClient.close()
    }

    private fun initView() {
//        recyclerView.layoutManager = GridLayoutManager(this@ItemDetailActivity,2)
        recyclerView.layoutManager =  LinearLayoutManager(this@ItemDetailActivity)



    }

    private fun handleItemClick() {
        println("Hello Detail")

    }

    private fun handlePlusClick(information: String) {

        println("Plus Button")
        val intent = Intent(this, ItemTopicActivity::class.java)
        startActivity(intent)
    }

    fun connect(applicationContext : Context) {
        mqttAndroidClient = MqttAndroidClient ( applicationContext,"tcp://m24.cloudmqtt.com:14029","1234234" )
        try {
            val mqttConnectOptions = MqttConnectOptions()
            mqttConnectOptions.isAutomaticReconnect = true
            mqttConnectOptions.isCleanSession = false
            mqttConnectOptions.userName = "plefajkt"
            mqttConnectOptions.password = "i5DuqlA-kT2q".toCharArray()
            mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1)


            val token = mqttAndroidClient.connect(mqttConnectOptions)
            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken)                        {
                    Log.i("Connection", "success ")
                    //connectionStatus = true
                    // Give your callback on connection established here
                    subscribe("test1")
                }
                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    //connectionStatus = false
                    Log.i("Connection", "failure")
                    // Give your callback on connection failure here
                    exception.printStackTrace()
                }
            }
        } catch (e: MqttException) {
            // Give your callback on connection failure here
            e.printStackTrace()
        }
    }


    fun subscribe(topic: String) {
        val qos = 2 // Mention your qos value
        try {
            mqttAndroidClient.subscribe(topic, qos, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    // Give your callback on Subscription here
                }
                override fun onFailure(
                    asyncActionToken: IMqttToken,
                    exception: Throwable
                ) {
                    // Give your subscription failure callback here
                }
            })
        } catch (e: MqttException) {
            // Give your subscription failure callback here
        }
    }

    fun unSubscribe(topic: String) {
        try {
            val unsubToken = mqttAndroidClient.unsubscribe(topic)
            unsubToken.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    // Give your callback on unsubscribing here
                }
                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    // Give your callback on failure here
                }
            }
        } catch (e: MqttException) {
            // Give your callback on failure here
        }
    }

    fun receiveMessages() {
        mqttAndroidClient.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable) {
                //connectionStatus = false
                // Give your callback on failure here
            }
            override fun messageArrived(topic: String, message: MqttMessage) {
                try {
                    val data = String(message.payload, charset("UTF-8"))
                    // data is the desired received message
                    // Give your callback on message received here
                } catch (e: Exception) {
                    // Give your callback on error here
                }
            }
            override fun deliveryComplete(token: IMqttDeliveryToken) {
                // Acknowledgement on delivery complete
            }
        })
    }

    fun publish(topic: String, data: String) {
        val encodedPayload : ByteArray
        try {
            encodedPayload = data.toByteArray(charset("UTF-8"))
            val message = MqttMessage(encodedPayload)
            message.qos = 2
            message.isRetained = false
            mqttAndroidClient.publish(topic, message)
        } catch (e: Exception) {
            // Give Callback on error here
        } catch (e: MqttException) {
            // Give Callback on error here
        }
    }

    fun disconnect() {
        try {
            val disconToken = mqttAndroidClient.disconnect()
            disconToken.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    //connectionStatus = false
                    // Give Callback on disconnection here
                }
                override fun onFailure(
                    asyncActionToken: IMqttToken,
                    exception: Throwable
                ) {
                    // Give Callback on error here
                }
            }
        } catch (e: MqttException) {
            // Give Callback on error here
        }
    }

    private fun disconnectClient() {
        if (mqttAndroidClient != null) {
            mqttAndroidClient.unregisterResources()
            mqttAndroidClient.close()
        }
    }
}
