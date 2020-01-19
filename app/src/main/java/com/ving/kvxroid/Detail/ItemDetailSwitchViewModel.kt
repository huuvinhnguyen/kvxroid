package com.ving.kvxroid.Detail

import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.extensions.empty

data class ItemDetailSwitchViewModel(
    val id: String = String.empty(),
    val name: String = String.empty(),
    val value: String = String.empty()
) : AnyObject