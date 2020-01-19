package com.ving.kvxroid.TopicDetailActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ving.kvxroid.R
import com.ving.kvxroid.setOnSafeClickListener
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.extensions.empty
import kotlinx.android.synthetic.main.topic_detail_login_view_holder.view.*

class TopicDetailAdapter(private val items: ArrayList<AnyObject>): RecyclerView.Adapter<TopicDetailBaseViewHolder<*>>() {

    companion object {
        private const val TYPE_HEADER = 2
        private const val TYPE_TOPIC = 0
        private const val TYPE_SERVER = 1
        private const val TYPE_SIGNIN = 3
    }

    private var data: List<Any> = emptyList()


    var onItemClick: (() -> Unit)? = null
    var onItemPlusClick: ((String) -> Unit)? = null
    var onItemLoginClick: ((String) -> Unit)? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicDetailBaseViewHolder<*> {
        return when (viewType) {

            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.topic_detail_header_view_holder, parent, false)
                return TopicDetailHeaderViewHolder(view)
            }

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

            TYPE_SIGNIN -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.topic_detail_login_view_holder, parent, false)
                return TopicDetailLoginViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view kind")

        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: TopicDetailBaseViewHolder<*>, position: Int) {

        val element = items[position]
        when (viewHolder) {
            is TopicDetailHeaderViewHolder -> viewHolder.bind(element as TopicDetailHeaderViewModel)
            is TopicDetailViewHolder -> viewHolder.bind(element as TopicDetailViewModel)
            is TopicDetailServerViewHolder -> viewHolder.bind(element as TopicDetailServerViewModel)
            is TopicDetailLoginViewHolder -> viewHolder.bind2(element as TopicDetailLoginViewModel, onItemLoginClick)
            else -> throw IllegalArgumentException()
        }
    }
    //
    override fun getItemViewType(position: Int): Int {
        val comparable = items[position]
        return when (comparable) {
            is TopicDetailHeaderViewModel -> TYPE_HEADER
            is TopicDetailViewModel -> TYPE_TOPIC
            is TopicDetailServerViewModel -> TYPE_SERVER
            is TopicDetailLoginViewModel -> TYPE_SIGNIN
            else -> throw IllegalArgumentException("Invalid kind of data " + position)
        }
    }

    fun setItems() {
        items.add(TopicDetailHeaderViewModel())
        items.add(TopicDetailViewModel("Topic Detail"))
        items.add(TopicDetailServerViewModel("Server 1 1"))
        items.add(TopicDetailLoginViewModel(""))

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
//            itemView.tvName.text = item.name
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
//            itemView.textView.text = item.name

        }
    }

    inner class TopicDetailHeaderViewHolder(itemView: View) :
        TopicDetailBaseViewHolder<TopicDetailHeaderViewModel>(itemView) {

        init {
            itemView.setOnSafeClickListener {
                onItemClick?.invoke()
            }
        }

        override fun bind(item: TopicDetailHeaderViewModel) {

        }
    }


    data class TopicDetailHeaderViewModel(val name: String = String.empty()) : AnyObject


    inner class TopicDetailLoginViewHolder(itemView: View) :
        TopicDetailBaseViewHolder<TopicDetailLoginViewModel>(itemView) {

        init {
            itemView.setOnSafeClickListener {
                onItemClick?.invoke()
            }
        }

        override fun bind(item: TopicDetailLoginViewModel) {


        }

        fun bind2(viewModel: TopicDetailLoginViewModel, clickListener: ((String) -> Unit)?) = with(itemView)  {
            itemView.btnLogin.setOnClickListener { clickListener?.invoke("abc")  }
        }
    }

    data class TopicDetailLoginViewModel(val name: String = String.empty()) : AnyObject



}