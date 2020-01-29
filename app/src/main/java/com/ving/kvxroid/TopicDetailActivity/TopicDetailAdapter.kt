package com.ving.kvxroid.TopicDetailActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ving.kvxroid.R
import com.ving.kvxroid.setOnSafeClickListener
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.extensions.empty
import kotlinx.android.synthetic.main.topic_detail_footer_view_holder.view.*
import kotlinx.android.synthetic.main.topic_detail_footer_view_holder.view.imageButton
import kotlinx.android.synthetic.main.topic_detail_header_view_holder.view.*
import kotlinx.android.synthetic.main.topic_detail_login_view_holder.view.*
import kotlinx.android.synthetic.main.topic_detail_view_holder.view.*

class TopicDetailAdapter(private val items: ArrayList<AnyObject>): RecyclerView.Adapter<TopicDetailBaseViewHolder<*>>() {

    companion object {
        private const val TYPE_HEADER = 2
        private const val TYPE_TOPIC = 0
        private const val TYPE_SERVER = 1
        private const val TYPE_SIGNIN = 3
        private const val TYPE_FOOTER = 4
    }

    private var data: List<Any> = emptyList()


    var onItemClick: (() -> Unit)? = null
    var onItemPlusClick: ((String) -> Unit)? = null
    var onItemLoginClick: ((String) -> Unit)? = null
    var onTrashClick: ((String) -> Unit)? = null



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

            TYPE_FOOTER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.topic_detail_footer_view_holder, parent, false)
                return TopicDetailFooterViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")

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
            is TopicDetailFooterViewHolder -> viewHolder.bind(element as TopicDetailFooterViewModel)
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
            is TopicDetailFooterViewModel -> TYPE_FOOTER
            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    fun setItems(list: ArrayList<AnyObject>) {
        items.clear()
        items.addAll(list)
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


            itemView.tvTopic.text  = item.topic
            itemView.tvValue.text = item.value
            itemView.tvTime.text = item.time
            itemView.tvQos.text = item.qos
            itemView.tvRetained.text = item.retained
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
            itemView.tvHeader.text = item.name

        }
    }


    data class TopicDetailHeaderViewModel(
        val name: String = String.empty()
    ) : AnyObject

    inner class TopicDetailFooterViewHolder(itemView: View) : TopicDetailBaseViewHolder<TopicDetailFooterViewModel>(itemView) {
        private lateinit var viewModel: TopicDetailFooterViewModel

        init {
            itemView.imageButton.setOnSafeClickListener {
                onTrashClick?.invoke(viewModel.id)
            }
        }

        override  fun bind(item: TopicDetailFooterViewModel) {
            viewModel = item
        }
    }

    data class TopicDetailFooterViewModel(
        val id: String = String.empty()
    ) : AnyObject


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