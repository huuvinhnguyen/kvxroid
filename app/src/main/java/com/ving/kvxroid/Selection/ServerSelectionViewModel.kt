package com.ving.kvxroid.Selection

import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.extensions.empty

data class ServerSelectionViewModel(
    val title: String = String.empty(),
    var isSelected: Boolean = false
) : AnyObject