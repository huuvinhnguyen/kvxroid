package com.ving.kvxroid.TopicDetailActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ving.kvxroid.R
import com.ving.kvxroid.setOnSafeClickListener
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.extensions.empty
import kotlinx.android.synthetic.main.topic_detail_gauge_view_holder.view.*
import kotlinx.android.synthetic.main.topic_detail_footer_view_holder.view.imageButton
import kotlinx.android.synthetic.main.topic_detail_header_view_holder.view.*
import kotlinx.android.synthetic.main.topic_detail_login_view_holder.view.*
import kotlinx.android.synthetic.main.topic_detail_server_view_holder.view.*
import kotlinx.android.synthetic.main.topic_detail_server_view_holder.view.tvName
import kotlinx.android.synthetic.main.topic_detail_view_holder.view.*

class TopicDetailAdapter(private val items: ArrayList<AnyObject>): RecyclerView.Adapter<TopicDetailBaseViewHolder<*>>() {

    companion object {
        private const val TYPE_HEADER = 2
        private const val TYPE_TOPIC = 0
        private const val TYPE_SERVER = 1
        private const val TYPE_SIGNIN = 3
        private const val TYPE_FOOTER = 4
        private const val TYPE_GAUGE = 5
    }

    private var data: List<Any> = emptyList()


    var onItemClick: (() -> Unit)? = null
    var onItemPlusClick: ((String) -> Unit)? = null
    var onLoginClick: ((String) -> Unit)? = null
    var onLogoutClick: ((String) -> Unit)? = null
    var onTrashClick: ((String) -> Unit)? = null
    var onEditTopicClick: ((String) -> Unit)? = null
    var onEditServerClick: ((String) -> Unit)? = null
    var onInfoClick: ((String) -> Unit)? = null


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

            TYPE_GAUGE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.topic_detail_gauge_view_holder, parent, false)
                return GaugeViewHolder(view)

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
            is TopicDetailServerViewHolder -> viewHolder.bind2(element as TopicDetailServerViewModel, onLogoutClick)
            is TopicDetailLoginViewHolder -> viewHolder.bind2(element as TopicDetailLoginViewModel, onLoginClick)
            is TopicDetailFooterViewHolder -> viewHolder.bind(element as TopicDetailFooterViewModel)
            is GaugeViewHolder -> viewHolder.bind(element as GaugeViewModel)

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
            is GaugeViewModel -> TYPE_GAUGE
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
            itemView.tvType.text = item.type
            itemView.tvRetained.text = item.retained
        }

    }

    inner class TopicDetailServerViewHolder(itemView: View) :
        TopicDetailBaseViewHolder<TopicDetailServerViewModel>(itemView) {

        lateinit var viewModel1: TopicDetailServerViewModel

        init {
            itemView.setOnSafeClickListener {
                onItemClick?.invoke()
            }

        }

        override fun bind(item: TopicDetailServerViewModel) {
//            this.viewModel = item
            itemView.tvName.text = item.name
            itemView.tvServer.text = item.url
            itemView.tvUser.text = item.username
            itemView.tvPassword.text = item.password
            itemView.tvPort.text = item.port
            itemView.tvSslPort.text = item.sslPort
        }

        fun bind2(item: TopicDetailServerViewModel, clickListener: ((String) -> Unit)?) = with(itemView)  {
            itemView.tvName.text = item.name
            itemView.tvServer.text = item.url
            itemView.tvUser.text = item.username
            itemView.tvPassword.text = item.password
            itemView.tvPort.text = item.port
            itemView.tvSslPort.text = item.sslPort
            itemView.btnLogout.setOnClickListener { clickListener?.invoke(item.topicId)  }

            itemView.btnEditServer.setOnSafeClickListener {
                onEditServerClick?.invoke(item.topicId)
            }
        }
    }

    inner class TopicDetailHeaderViewHolder(itemView: View) :
        TopicDetailBaseViewHolder<TopicDetailHeaderViewModel>(itemView) {

        private lateinit var viewModel: TopicDetailHeaderViewModel

        init {
            itemView.setOnSafeClickListener {
                onItemClick?.invoke()
            }

            itemView.btnEditTopic.setOnSafeClickListener {
                onEditTopicClick?.invoke(viewModel.id)
            }
        }

        override fun bind(item: TopicDetailHeaderViewModel) {
            this.viewModel = item
            itemView.tvHeader.text = item.name

        }
    }

    inner class GaugeViewHolder(itemView: View) : TopicDetailBaseViewHolder<GaugeViewModel>(itemView) {

        init {

            itemView.btnInfo.setOnSafeClickListener {
                //onInfoClick?.invoke(viewModel.id)
            }


        }

        private lateinit var viewModel: GaugeViewModel

        override fun bind(viewModel: GaugeViewModel) {
            this.viewModel = viewModel
            itemView.gaugeView.unit = "°C"
            val value = viewModel.value?.toFloatOrNull() ?: 0F
            itemView.gaugeView.speedTo(value, 4000)
            itemView.tvName.text = viewModel.name


        }
    }

    data class GaugeViewModel(
        val id: String = String.empty(),
        val name: String = String.empty(),
        var value: String = String.empty(),
        var message: String = String.empty()
    ) : AnyObject


    data class TopicDetailHeaderViewModel(
        val id: String = String.empty(),
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
            itemView.btnLogin.setOnClickListener { clickListener?.invoke(viewModel.id)  }
        }
    }

    data class TopicDetailLoginViewModel(val id: String = String.empty()) : AnyObject

}