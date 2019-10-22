package com.ving.kvxroid.TopicDetailActivity

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class TopicDetailBaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}