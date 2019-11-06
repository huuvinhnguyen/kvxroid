package com.ving.kvxroid.Services

import android.content.Context
import com.ving.kvxroid.Common.BaseApplication
import com.ving.kvxroid.Selection.ConnectionRealm
import com.ving.kvxroid.Selection.ItemRealm
import com.ving.kvxroid.Selection.ServerRealm
import com.ving.kvxroid.Selection.TopicRealm
import io.realm.Realm
import java.util.*

class RealmInteractor {

    private lateinit var context: Context

    @Suppress("NAME_SHADOWING")
    fun connectRealm() {

        val context = BaseApplication.INSTANCE.applicationContext

        Realm.init(context)

//        Realm.init(this@ItemTopicActivity)

        val realm = Realm.getDefaultInstance()

//        realm.executeTransaction { realm ->
//            realm.deleteAll()
//        }

//
//        // All writes must be wrapped in a transaction to facilitate safe multi threading
//        realm.executeTransaction { realm ->
//            // Add a person
//            val person = realm.createObject(Person::class.java, "fsdfdsfs".toString())
//        }

//        realm.executeTransaction { realm ->
//            // Add a person
//            val server = realm.createObject(Dog::class.java, "fsdfdsfs".toString())
//        }
//
//        realm.executeTransaction { realm ->
//            // Add a person
//            var uniqueID = UUID.randomUUID().toString()
//            val server = realm.createObject(ServerRealm::class.java, uniqueID)
//            server.name =  ""
//
//            server.port = "123"
//        }

        realm.executeTransaction { realm ->
            // Add a person
            var uniqueID = UUID.randomUUID().toString()
            val connection = realm.createObject(ConnectionRealm::class.java, uniqueID)
            connection.server = "server abcde"
            connection.user = "suer asfdsfsd"
            connection.port = "123"
        }

        // Find the first person (no query conditions) and read a field
//        val person = realm.where<Person>("gogl").findFirst()!!

//        // Update person in a transaction
//        realm.executeTransaction { _ ->
//            person.name = "Senior Person"
//        }

//        val results = realm.where<Person>().equalTo("", "").findAll()

//        var guest: Person = realm.createObject(Person::class.java, UUID.randomUUID().toString())


        var list = realm.where(ServerRealm::class.java).findAll()
        list.forEach { person ->

            println(person)
        }

        val listStrings = list.map { serverRealm -> serverRealm.name }


    }

    fun getConnections(): List<ConnectionRealm> {

        val context = BaseApplication.INSTANCE.applicationContext
        Realm.init(context)
        val realm = Realm.getDefaultInstance()

        val list = realm.where(ConnectionRealm::class.java).findAll()
        val listStrings = list.map { conenctionRealm -> conenctionRealm.server }
        return list

    }

    fun addTopic(topic: TopicRealm, finished: () -> Unit) {

        val context = BaseApplication.INSTANCE.applicationContext

        Realm.init(context)

        val realm = Realm.getDefaultInstance()

        realm.executeTransactionAsync({ realm ->
            val item = realm.createObject(TopicRealm::class.java, topic.id)
            item.name = topic.name

        }, {
            finished()
            realm.close()
        },
            { realm.close() })

    }

    fun deleteTopic(id: String, finished: (String) ->Unit) {

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

        }, {
            finished(item)
            realm.close()
        },
            { realm.close() })
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