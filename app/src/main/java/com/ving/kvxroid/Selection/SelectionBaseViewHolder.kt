package com.ving.kvxroid.Selection

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class SelectionBaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: T)
}
