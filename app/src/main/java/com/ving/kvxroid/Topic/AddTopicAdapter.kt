package com.ving.kvxroid.Topic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Detail.ItemDetailBaseViewHolder
import com.ving.kvxroid.R
import com.ving.kvxroid.extensions.empty
import kotlinx.android.extensions.LayoutContainer

class AddTopicAdapter(private val items: ArrayList<AnyObject>): RecyclerView.Adapter<AddTopicBaseViewHolder<*>>() {

    companion object {
        private const val TYPE_TOPIC = 0
        private const val TYPE_QOS = 2
        private const val TYPE_SAVE = 1
        private const val TYPE_SWITCH =3


    }

    var onSaveClick: ((String) -> Unit)? = null
    var onSelectClick: ((String) -> Unit)? = null

    override fun onBindViewHolder(holder: AddTopicBaseViewHolder<*>, position: Int) {
        val element = items[position]
        when (holder) {
            is TopicViewHolder -> holder.bind(element as TopicViewModel, onSelectClick)
            is TopicQosViewHolder -> holder.bind(element as TopicQosViewModel)
            is TopicSaveViewHolder -> {
                holder.bind(element as TopicSaveViewModel, onSaveClick)

            }
            is TopicSwitchViewHolder -> holder.bind(element as TopicSwitchViewModel)

            else -> throw IllegalArgumentException()
        }

    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddTopicBaseViewHolder<*> {

        return return when (viewType) {
            TYPE_TOPIC -> {
                return TopicViewHolder.renderView(parent)
            }

            TYPE_QOS -> {
                return TopicQosViewHolder.renderView(parent)
            }
            TYPE_SAVE -> {
                return TopicSaveViewHolder.renderView(parent)
            }

            TYPE_SWITCH -> TopicSwitchViewHolder.renderView(parent)

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val comparable = items[position]
        return when (comparable) {
            is TopicViewModel -> TYPE_TOPIC
            is TopicQosViewModel -> TYPE_QOS
            is TopicSaveViewModel -> TYPE_SAVE
            is TopicSwitchViewModel -> TYPE_SWITCH

            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    fun setItems() {

        notifyDataSetChanged()
    }

}
