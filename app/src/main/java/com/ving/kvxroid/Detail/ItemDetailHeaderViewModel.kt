package com.ving.kvxroid.Detail

import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.extensions.empty

data class ItemDetailHeaderViewModel(
    val title: String = String.empty(),
    val imageUrl: String = String.empty()
) : AnyObject