package com.ving.kvxroid.TopicDetailActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ving.kvxroid.R
import com.ving.kvxroid.setOnSafeClickListener
import com.ving.kvxroid.AnyObject
import kotlinx.android.synthetic.main.topic_detail_server_view_holder.view.*
import kotlinx.android.synthetic.main.topic_detail_server_view_holder.view.textView
import kotlinx.android.synthetic.main.topic_detail_view_holder.view.*

class TopicDetailAdapter(private val items: ArrayList<AnyObject>): RecyclerView.Adapter<TopicDetailBaseViewHolder<*>>() {

    companion object {
        private const val TYPE_TOPIC = 0
        private const val TYPE_SERVER = 1


    }

    private var data: List<Any> = emptyList()


    var onItemClick: (() -> Unit)? = null
    var onItemPlusClick: ((String) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicDetailBaseViewHolder<*> {
        return when (viewType) {
            TYPE_TOPIC -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.topic_detail_view_holder, parent, false)
                return TopicDetailViewHolder(view)
            }
            TYPE_SERVER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.topic_detail_server_view_holder, parent, false)
                return TopicDetailServerViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")

        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: TopicDetailBaseViewHolder<*>, position: Int) {

        val element = items[position]
        when (viewHolder) {
            is TopicDetailViewHolder -> viewHolder.bind(element as TopicDetailViewModel)
            is TopicDetailServerViewHolder -> viewHolder.bind(element as TopicDetailServerViewModel)
            else -> throw IllegalArgumentException()
        }
    }
    //
    override fun getItemViewType(position: Int): Int {
        val comparable = items[position]
        return when (comparable) {
            is TopicDetailViewModel -> TYPE_TOPIC
            is TopicDetailServerViewModel -> TYPE_SERVER
            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    fun setItems() {
        items.add(TopicDetailViewModel("Topic Detail"))
        items.add(TopicDetailServerViewModel("Server 1 1"))


        notifyDataSetChanged()
    }

    inner class TopicDetailViewHolder(itemView: View) :
        TopicDetailBaseViewHolder<TopicDetailViewModel>(itemView) {

        init {
            itemView.setOnSafeClickListener {
                onItemClick?.invoke()
            }
        }

        override fun bind(item: TopicDetailViewModel) {
//            itemView.textView = item.name
            itemView.textView.text = item.name
        }

    }

    inner class TopicDetailServerViewHolder(itemView: View) :
        TopicDetailBaseViewHolder<TopicDetailServerViewModel>(itemView) {

        init {
            itemView.setOnSafeClickListener {
                onItemClick?.invoke()
            }
        }

        override fun bind(item: TopicDetailServerViewModel) {
            itemView.textView.text = item.name

        }
    }


}