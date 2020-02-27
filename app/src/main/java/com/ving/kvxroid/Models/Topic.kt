package com.ving.kvxroid.Models

import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.extensions.empty

data class Topic(
    var id: String = String.empty(),
    var name: String = String.empty(),
    var topic: String = String.empty(),
    var value: String = String.empty(),
    var time: String = String.empty(),
    var serverId: String = String.empty(),
    var type: String = String.empty(),
    var qos: String = String.empty(),
    var retain: String = String.empty(),
    var itemId: String = String.empty()
    ): AnyObject

