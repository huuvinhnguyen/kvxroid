package com.ving.kvxroid.Detail

import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class ItemDetailBaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}

