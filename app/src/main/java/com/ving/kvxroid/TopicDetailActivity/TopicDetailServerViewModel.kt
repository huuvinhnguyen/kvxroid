package com.ving.kvxroid.TopicDetailActivity

import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.extensions.empty

data class TopicDetailServerViewModel(
    val serverId: String = String.empty(),
    val topicId: String = String.empty(),
    val name: String = String.empty(),
    val url: String = String.empty(),
    val username: String = String.empty(),
    val password: String = String.empty(),
    val port: String = String.empty(),
    val sslPort: String = String.empty()

) : AnyObject