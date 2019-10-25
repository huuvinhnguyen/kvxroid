package com.ving.kvxroid.Redux

import android.content.Context
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Common.BaseApplication
import com.ving.kvxroid.Detail.ItemDetailHeaderViewModel
import com.ving.kvxroid.Detail.ItemDetailPlusViewModel
import com.ving.kvxroid.Detail.ItemDetailSwitchViewModel
import com.ving.kvxroid.Selection.*
import org.rekotlin.Action
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_item_topic.*
import java.util.*
import kotlin.collections.ArrayList


fun counterReducer(action: Action, state: AppState?): AppState {
    // if no state has been provided, create the default state
    var state = state ?: AppState()

    when(action){
        is CounterActionIncrease -> {
            state = state.copy(counter = state.counter + 1)
        }
        is CounterActionDecrease -> {
            state = state.copy(counter = state.counter - 1)
        }
        is ItemListStateLoad -> {

            val items: ArrayList<AnyObject> = ArrayList()
            items.add(ItemDetailHeaderViewModel("Header abc"))
            items.add(ItemDetailSwitchViewModel("switch 1"))
            items.add(ItemDetailSwitchViewModel("switch 2"))
            items.add(ItemDetailPlusViewModel())

            val topicList = RealmInteractor().getTopics()
            val list = topicList.forEach { topicRealm ->
                val viewModel = ItemDetailSwitchViewModel("sffffff")
                items.add(viewModel)
            }

            state = state.copy(itemDetailList = items)

        }

        is ConnectionActionAdd -> {
            val items: ArrayList<AnyObject> = ArrayList()

            val realmInteractor = RealmInteractor()
            realmInteractor.connectRealm()


        }

        is ConnectionActionLoad -> {
//            val items: ArrayList<AnyObject> = ArrayList()

//            items.add(SelectionTypeViewModel("Type here"))
//            items.add(SelectionServerViewModel("Server here"))
//            items.add(SelectionServerViewModel("Server here aaaa"))
            val connectionList = RealmInteractor().getConnections()
            val items = connectionList.map { connectionRealm ->
                SelectionServerViewModel("sffffff")
            }

            state = state.copy(connectionList = items)

        }

        is TopicActionAdd -> {
            val realmInteractor = RealmInteractor()
            realmInteractor.addTopic()

        }
    }

    return state
}

class RealmInteractor {

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

        val listStrings = list.map { serverRealm ->  serverRealm.name }


    }

    fun getConnections():  List<ConnectionRealm> {

        val context = BaseApplication.INSTANCE.applicationContext
        Realm.init(context)
        val realm = Realm.getDefaultInstance()

        val list = realm.where(ConnectionRealm::class.java).findAll()
        val listStrings = list.map { conenctionRealm ->  conenctionRealm.server }
        return list

    }

    fun addTopic() {

        val context = BaseApplication.INSTANCE.applicationContext

        Realm.init(context)

        val realm = Realm.getDefaultInstance()

        realm.executeTransaction { realm ->
            // Add a person
            var uniqueID = UUID.randomUUID().toString()
            val topicRealm = realm.createObject(TopicRealm::class.java, uniqueID)
            topicRealm.name = "Item 1"
        }
    }

    fun getTopics():  List<TopicRealm> {

        val context = BaseApplication.INSTANCE.applicationContext
        Realm.init(context)
        val realm = Realm.getDefaultInstance()

        val list = realm.where(TopicRealm::class.java).findAll()
        return list

    }
}


