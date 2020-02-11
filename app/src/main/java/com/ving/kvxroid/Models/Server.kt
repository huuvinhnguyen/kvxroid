package com.ving.kvxroid.Models

import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.extensions.empty

data class Server(
    var id: String = String.empty(),
    var name: String = String.empty(),
    var url: String = String.empty(),
    var user: String = String.empty(),
    var password: String = String.empty(),
    var port: String = String.empty(),
    var sslPort: String = String.empty()

): AnyObject
