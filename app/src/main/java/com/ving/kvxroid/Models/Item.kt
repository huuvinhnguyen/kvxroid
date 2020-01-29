package com.ving.kvxroid.Models

import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.extensions.empty

data class Item(
    var id: String,
    var name: String = String.empty(),
    var imageUrl: String = ""
) : AnyObject