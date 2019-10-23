package com.ving.kvxroid.Redux

import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Common.BaseApplication
import com.ving.kvxroid.Detail.ItemDetailHeaderViewModel
import com.ving.kvxroid.Detail.ItemDetailPlusViewModel
import com.ving.kvxroid.Detail.ItemDetailSwitchViewModel
import com.ving.kvxroid.Selection.ServerRealm
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

            state = state.copy(itemDetailList = items)

        }

        is ConnectionActionAdd -> {
            val items: ArrayList<AnyObject> = ArrayList()
        }

        is ConnectionActionLoad -> {
            val items: ArrayList<AnyObject> = ArrayList()
            

            state = state.copy(connectionList = items)

        }
    }

    return state
}

class A {

    @Suppress("NAME_SHADOWING")
    fun connectRealm() {

        val context = BaseApplication().applicationContext

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
        realm.executeTransaction { realm ->
            // Add a person
            var uniqueID = UUID.randomUUID().toString()
            val server = realm.createObject(ServerRealm::class.java, uniqueID)
            server.name =  ""

            server.port = "123"
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
    }
}


