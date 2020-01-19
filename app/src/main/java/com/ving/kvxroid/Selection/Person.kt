package com.ving.kvxroid.Selection

import io.realm.annotations.PrimaryKey
import io.realm.RealmObject


open class Person : RealmObject() {
    @PrimaryKey
     var name: String? = null

    // ... Generated getters and setters ...
}

open class User : RealmObject() {

    @PrimaryKey
    var name: String? = null


}

open class ServerRealm : RealmObject() {

    @PrimaryKey
    var id: String? = null
    var name: String? = null
    var port: String? = null


}


open class ConnectionRealm : RealmObject() {

    @PrimaryKey
    var id: String? = null
    var server: String? = null
    var user: String? = null
    var pass: String? = null
    var port: String? = null


}

open class TopicRealm : RealmObject() {

    @PrimaryKey
    var id: String? = null
    var name: String? = null
    var value: String? = null
    var serverId: String? = null
    var kind: String? = null
    var topic: String? = null
    var time: String? = null
    var retain: String? = null

}

open class ItemRealm() : RealmObject() {

    @PrimaryKey
    var id: String? = null
    var name: String? = null
    var imageUrl: String? = null

}


