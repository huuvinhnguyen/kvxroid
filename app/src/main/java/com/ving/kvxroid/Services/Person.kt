package com.ving.kvxroid.Services

import io.realm.annotations.PrimaryKey
import io.realm.RealmObject


open class User : RealmObject() {

    @PrimaryKey
    var name: String? = null


}

open class ServerRealm : RealmObject() {

    @PrimaryKey
    var id: String? = null
    var name: String? = null
    var url: String? = null
    var user: String? = null
    var password: String? = null
    var port: String? = null
    var sslPort: String? = null


}

open class TopicRealm : RealmObject() {

    @PrimaryKey
    var id: String? = null
    var name: String? = null
    var value: String? = null
    var serverId: String? = null
    var type: String? = null
    var topic: String? = null
    var time: String? = null
    var retain: String? = null
    var itemId: String? = null
    var qos: String? = null

}

open class ItemRealm() : RealmObject() {

    @PrimaryKey
    var id: String? = null
    var name: String? = null
    var imageUrl: String? = null

}


