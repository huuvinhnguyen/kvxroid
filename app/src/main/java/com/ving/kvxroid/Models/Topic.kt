package com.ving.kvxroid.Models

import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.extensions.empty

data class Topic(
    var id: String,
    var name: String = String.empty(),
    var topic: String = String.empty(),
    var value: String = String.empty(),
    var time: String = String.empty(),
    var serverId: String = String.empty(),
    var type: String = String.empty(),
    var qos: String = String.empty()

    ): AnyObject

