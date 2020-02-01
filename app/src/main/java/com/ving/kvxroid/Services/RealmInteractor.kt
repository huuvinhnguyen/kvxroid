package com.ving.kvxroid.Services

import android.content.Context
import com.ving.kvxroid.Common.BaseApplication
import io.realm.Realm
import io.realm.kotlin.where
import java.util.*

class RealmInteractor {

    private lateinit var context: Context

    @Suppress("NAME_SHADOWING")
    fun connectRealm() {

        val context = BaseApplication.INSTANCE.applicationContext

        Realm.init(context)

        val realm = Realm.getDefaultInstance()

        realm.executeTransaction { realm ->
            // Add a person
            var uniqueID = UUID.randomUUID().toString()
            val server = realm.createObject(ServerRealm::class.java, uniqueID)
            server.url = "server abcde"
            server.user = "suer asfdsfsd"
            server.port = "123"
        }

//        var list = realm.where(ServerRealm::class.java).findAll()
//        list.forEach { person ->
//
//            println(person)
//        }
//
//        val listStrings = list.map { serverRealm -> serverRealm.name }


    }

    fun getServer(id: String, finished: (ServerRealm?) -> Unit) {

        val context = BaseApplication.INSTANCE.applicationContext

        Realm.init(context)

        val realm = Realm.getDefaultInstance()

        val itemRef = realm.where<ServerRealm>().equalTo("id", id).findFirst()

        finished(itemRef)
    }

    fun getServers(): List<ServerRealm> {

        val context = BaseApplication.INSTANCE.applicationContext
        Realm.init(context)
        val realm = Realm.getDefaultInstance()

        val list = realm.where(ServerRealm::class.java).findAll()
        return list

    }

    fun addServer(server: ServerRealm, finished: () -> Unit) {

        val context = BaseApplication.INSTANCE.applicationContext

        Realm.init(context)

        val realm = Realm.getDefaultInstance()

        realm.executeTransactionAsync({ realm ->
            val item = realm.createObject(ServerRealm::class.java, server.id)
            item.name = server.name
            item.url = server.url
            item.user = server.user
            item.password = server.password
            item.port = server.port
            item.sslPort = server.sslPort

        }, {
            finished()
            realm.close()
        },
            { realm.close() })

    }

    fun updateServer(item: ServerRealm, finished: (String) -> Unit) {

        val context = BaseApplication.INSTANCE.applicationContext

        Realm.init(context)

        val realm = Realm.getDefaultInstance()

        val itemRef = realm.where<ServerRealm>().equalTo("id", item.id).findFirst()!!

        realm.executeTransaction { _ ->
            itemRef.name = item.name
            itemRef.url = item.url
            itemRef.user = item.user
            itemRef.password = item.password
            itemRef.port = item.port
            itemRef.sslPort = item.sslPort

        }

        finished(itemRef.id ?: "")
    }

    fun deleteServer(id: String, finished: (String) -> Unit) {

        val context = BaseApplication.INSTANCE.applicationContext

        Realm.init(context)

        val realm = Realm.getDefaultInstance()

        realm.executeTransaction { realm ->

            val results = realm.where(ServerRealm::class.java).equalTo("id", id).findAll()
            results.deleteAllFromRealm()

        }

        finished(id)

    }

    fun addTopic(topic: TopicRealm, finished: () -> Unit) {

        val context = BaseApplication.INSTANCE.applicationContext

        Realm.init(context)

        val realm = Realm.getDefaultInstance()

        realm.executeTransactionAsync({ realm ->
            val item = realm.createObject(TopicRealm::class.java, topic.id)
            item.name = topic.name
            item.value = topic.value
            item.serverId = topic.serverId
            item.type = topic.type
            item.topic = topic.topic
            item.time = topic.time
            item.retain = topic.retain
            item.itemId = topic.itemId
            item.qos = topic.qos


        }, {
            finished()
            realm.close()
        },
            { realm.close() })

    }

    fun deleteTopic(id: String, finished: (String) -> Unit) {

        val context = BaseApplication.INSTANCE.applicationContext

        Realm.init(context)

        val realm = Realm.getDefaultInstance()

        realm.executeTransaction { realm ->

            val results = realm.where(TopicRealm::class.java).equalTo("id", id).findAll()
            results.deleteAllFromRealm()

        }

        finished(id)
    }

    fun getTopics(): List<TopicRealm> {

        val context = BaseApplication.INSTANCE.applicationContext
        Realm.init(context)
        val realm = Realm.getDefaultInstance()

        val list = realm.where(TopicRealm::class.java).findAll()
        return list

    }

    fun getTopic(id: String, finished: (TopicRealm?) -> Unit) {
        val context = BaseApplication.INSTANCE.applicationContext

        Realm.init(context)

        val realm = Realm.getDefaultInstance()

        val itemRef = realm.where<TopicRealm>().equalTo("id", id).findFirst()

        finished(itemRef)
    }

    fun updateTopic(item: TopicRealm, finished: (String) -> Unit) {

        val context = BaseApplication.INSTANCE.applicationContext

        Realm.init(context)

        val realm = Realm.getDefaultInstance()

        val itemRef = realm.where<TopicRealm>().equalTo("id", item.id).findFirst()!!

        realm.executeTransaction { _ ->
            itemRef.name = item.name
            itemRef.value = item.value
            itemRef.serverId = item.serverId
            itemRef.type = item.type
            itemRef.topic = item.topic
            itemRef.time = item.time
            itemRef.retain = item.retain
            itemRef.itemId = item.itemId
            itemRef.qos = item.qos

        }

        finished(itemRef.id ?: "")

    }

    fun getItem(id: String, finished: (ItemRealm?) -> Unit) {
        val context = BaseApplication.INSTANCE.applicationContext

        Realm.init(context)

        val realm = Realm.getDefaultInstance()

        val itemRef = realm.where<ItemRealm>().equalTo("id", id).findFirst()

        finished(itemRef)


    }

    fun getItems(): List<ItemRealm> {

        val context = BaseApplication.INSTANCE.applicationContext
        Realm.init(context)
        val realm = Realm.getDefaultInstance()

        val list = realm.where(ItemRealm::class.java).findAll()
        return list
    }

    fun addItem(item: ItemRealm, finished: (ItemRealm) -> Unit) {

        val context = BaseApplication.INSTANCE.applicationContext

        Realm.init(context)

        val realm = Realm.getDefaultInstance()

        realm.executeTransactionAsync({ realm ->

            val itemRef = realm.createObject(ItemRealm::class.java, item.id)
            itemRef.name = item.name
            itemRef.imageUrl = item.imageUrl

        }, {
            finished(item)
            realm.close()
        },
            { realm.close() })
    }

    fun updateItem(item: ItemRealm, finished: (String) -> Unit) {

        val context = BaseApplication.INSTANCE.applicationContext

        Realm.init(context)

        val realm = Realm.getDefaultInstance()

        val itemRef = realm.where<ItemRealm>().equalTo("id", item.id).findFirst()!!

        realm.executeTransaction { _ ->
            itemRef.name = item.name
            itemRef.imageUrl = item.imageUrl

        }

        finished(itemRef.id ?: "")
    }

    fun deleteItem(id: String, finished: (String) -> Unit) {
        val context = BaseApplication.INSTANCE.applicationContext

        Realm.init(context)

        val realm = Realm.getDefaultInstance()

        realm.executeTransaction { realm ->

            val results = realm.where(ItemRealm::class.java).equalTo("id", id).findAll()
            results.deleteAllFromRealm()

        }

        finished(id)

    }
}