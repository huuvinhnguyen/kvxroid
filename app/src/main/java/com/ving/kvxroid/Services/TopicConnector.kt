package com.ving.kvxroid.Services
import android.content.Context
import android.util.Log
import com.ving.kvxroid.Common.BaseApplication
import com.ving.kvxroid.Models.Server
import com.ving.kvxroid.Models.Topic
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class TopicConnector {

    private lateinit var mqttAndroidClient: MqttAndroidClient
    lateinit var topic: Topic
    private lateinit var server: Server


    var didReceiveMessage: ((String)->Unit)? = null

    companion object {
        const val TAG = "MqttClient"
    }


    fun disconnect() {
        mqttAndroidClient.unregisterResources()
        mqttAndroidClient.close()
        didReceiveMessage = null
    }

    fun configure(topic: Topic, server: Server) {

        this.topic = topic
        this.server = server

       connect()

    }

    fun connect2() {

        //example https://gist.github.com/hussanhijazi/4fd7c737ccb4f1006ad2e36f3108ddcc
//        val context = BaseApplication.INSTANCE.applicationContext
//
//        mqttAndroidClient = MqttAndroidClient ( context,server.url,topic.id )
//        try {
//            val mqttConnectOptions = MqttConnectOptions()
//            mqttConnectOptions.isAutomaticReconnect = true
//            mqttConnectOptions.isCleanSession = false
//            mqttConnectOptions.userName = server.user
//            mqttConnectOptions.password = server.password.toCharArray()
//            mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1)
//
//            val token = mqttAndroidClient.connect(mqttConnectOptions)
//            token.actionCallback = object : IMqttActionListener {
//                override fun onSuccess(asyncActionToken: IMqttToken)                        {
//                    Log.i("Connection", "success ")
//                    // Give your callback on connection established here
//                    subscribe(topic.topic)
//                    receiveMessages()
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
    }

    fun connect() {

        val context = BaseApplication.INSTANCE.applicationContext

        mqttAndroidClient = MqttAndroidClient ( context,"tcp://m24.cloudmqtt.com:14029",topic.id )
        try {
            val mqttConnectOptions = MqttConnectOptions()
            mqttConnectOptions.isAutomaticReconnect = false
//            mqttConnectOptions.keepAliveInterval =  1000* 60
            mqttConnectOptions.isCleanSession = false
            mqttConnectOptions.userName = "plefajkt"
            mqttConnectOptions.password = "i5DuqlA-kT2q".toCharArray()
            mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1)

            val token = mqttAndroidClient.connect(mqttConnectOptions)
            token.actionCallback = object : IMqttActionListener {

                override fun onSuccess(asyncActionToken: IMqttToken)                        {
                    Log.i("Connection", "success ")
                    // Give your callback on connection established here
                    subscribe("switch")
                    receiveMessages()
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

    private fun subscribe(topic: String) {
        val qos = 2 // Mention your qos value
        try {
            mqttAndroidClient.connect()

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

    private fun unSubscribe(topic: String) {
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

    private fun receiveMessages() {
        val value = this.topic.value
        mqttAndroidClient.setCallback(object : MqttCallbackExtended {
//            override fun connectionLost(cause: Throwable) {
//                //connectionStatus = false
//                // Give your callback on failure here
//            }
//            override fun messageArrived(topic: String, message: MqttMessage) {
//                try {
////                    val data = String(message.payload, charset("UTF-8"))
//                    // data is the desired received message
//                    // Give your callback on message received here
//                    if (value != message.toString()) {
//                        didReceiveMessage?.invoke(message.toString())
//                    }
////                    print(message.toString())
////                    Log.i("Received", message.toString())
//                } catch (e: Exception) {
//                    // Give your callback on error here
//                }
//            }
//            override fun deliveryComplete(token: IMqttDeliveryToken) {
//                // Acknowledgement on delivery complete
//            }

            override fun connectComplete(reconnect: Boolean, serverURI: String) {
               subscribe("switch")
                Log.d(TAG, "Connected to: $serverURI")
            }

            override fun connectionLost(cause: Throwable) {
                Log.d(TAG, "The Connection was lost.")
            }

            @Throws(Exception::class)
            override fun messageArrived(topic: String, message: MqttMessage) {
                Log.d(TAG, "Incoming message from $topic: " + message.toString())
                didReceiveMessage?.invoke(message.toString())
            }

            override fun deliveryComplete(token: IMqttDeliveryToken) {

            }
        })
    }

    fun publish(data: String) {
        val encodedPayload : ByteArray
        try {
            encodedPayload = data.toByteArray(charset("UTF-8"))
            val message = MqttMessage(encodedPayload)
            message.qos = 2
            message.isRetained = false
            mqttAndroidClient.publish(this.topic.topic, message)
        } catch (e: Exception) {
            // Give Callback on error here
        } catch (e: MqttException) {
            // Give Callback on error here
        }
    }

}