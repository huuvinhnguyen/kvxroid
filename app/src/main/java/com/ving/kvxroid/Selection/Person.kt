package com.ving.kvxroid.Selection

import io.realm.RealmList
import io.realm.annotations.PrimaryKey
import io.realm.RealmObject
import io.realm.annotations.Ignore


open class Person : RealmObject() {
    @PrimaryKey
     var name: String? = null

    // ... Generated getters and setters ...
}

open class User : RealmObject() {

    var name: String? = null


}