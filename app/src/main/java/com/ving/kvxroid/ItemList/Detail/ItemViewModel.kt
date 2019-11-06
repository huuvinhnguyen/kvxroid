package com.ving.kvxroid.ItemList.Detail

import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.extensions.empty

data class ItemViewModel(
    var id: String,
    val name: String = String.empty()
) : AnyObject