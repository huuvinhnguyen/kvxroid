package com.ving.kvxroid.Topic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.R
import com.ving.kvxroid.extensions.empty
import com.ving.kvxroid.setOnSafeClickListener
import kotlinx.android.synthetic.main.topic_type_view_holder.view.*
import kotlinx.android.synthetic.main.topic_type_view_holder.view.checkBox

class TopicTypeAdapter(private val items: ArrayList<AnyObject>) :
    RecyclerView.Adapter<TopicTypeAdapter.BaseViewHolder<*>>() {

    companion object {
        private const val TYPE_TYPE = 0

    }

    var onItemClick: ((ItemViewModel) -> Unit)? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.topic_type_view_holder, parent, false)
                return SwitchViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")

        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: BaseViewHolder<*>, position: Int) {

        val element = items[position]
        when (viewHolder) {
            is SwitchViewHolder -> viewHolder.bind(element as ItemViewModel)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val comparable = items[position]
        return when (comparable) {
            is ItemViewModel -> TYPE_TYPE
            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    fun setItems(list: ArrayList<AnyObject>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }


    inner class SwitchViewHolder(itemView: View) :
        BaseViewHolder<ItemViewModel>(itemView) {

        private lateinit var viewModel: ItemViewModel

        init {
            itemView.setOnSafeClickListener {
                onItemClick?.invoke(viewModel)
            }
            itemView.checkBox.setOnSafeClickListener {
                onItemClick?.invoke(viewModel)
            }
        }

        override fun bind(item: ItemViewModel) {
            this.viewModel = item
            itemView.checkBox.isChecked = viewModel.isSelected
            itemView.tvType.text = item.title
        }
    }

    data class ItemViewModel(
        val title: String = String.empty(),
        var isSelected: Boolean = false
    ) : AnyObject

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }


}