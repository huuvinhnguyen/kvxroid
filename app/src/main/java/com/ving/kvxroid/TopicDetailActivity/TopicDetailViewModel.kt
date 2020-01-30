package com.ving.kvxroid.TopicDetailActivity

import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.extensions.empty

data class TopicDetailViewModel(
    var id: String = String.empty(),
    val topic: String = String.empty(),
    val value: String = String.empty(),
    val time: String = String.empty(),
    val qos: String = String.empty(),
    val retained: String = String.empty()

) : AnyObject