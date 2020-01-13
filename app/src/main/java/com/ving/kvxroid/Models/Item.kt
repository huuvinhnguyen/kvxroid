package com.ving.kvxroid.Models

import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.extensions.empty

data class Item(
    var id: String,
    val name: String = String.empty(),
    val imageUrl: String = ""
) : AnyObject