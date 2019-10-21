package com.ving.kvxroid.TopicDetailActivity

import com.ving.kvxroid.Detail.ItemDetailBaseViewHolder
import com.ving.kvxroid.Detail.ItemDetailHeaderViewModel
import com.ving.kvxroid.Detail.ItemDetailPlusViewModel
import com.ving.kvxroid.Detail.ItemDetailSwitchViewModel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ving.kvxroid.R
import com.ving.kvxroid.setOnSafeClickListener
import kotlinx.android.extensions.LayoutContainer
import com.ving.kvxroid.AnyObject
import kotlinx.android.synthetic.main.activity_item_detail_header.view.textView
import kotlinx.android.synthetic.main.item_detail_plus_view_holder.view.*

class ItemDetailRecyclerAdapter(private val items: ArrayList<AnyObject>): RecyclerView.Adapter<ItemDetailBaseViewHolder<*>>() {

    companion object {
        private const val TYPE_TOPIC = 0
        private const val TYPE_SERVER = 1


    }

    private var data: List<Any> = emptyList()


    //    private var listOfMovies = listOf<MovieModel>()
    var onItemClick: (() -> Unit)? = null
    var onItemPlusClick: ((String) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDetailBaseViewHolder<*> {
        return when (viewType) {
            TYPE_TOPIC -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_detail_header_view_holder, parent, false)
                return HeaderViewHolder(view)
            }
            TYPE_SERVER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_detail_switch_view_holder, parent, false)
                return SwitchViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")

        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: ItemDetailBaseViewHolder<*>, position: Int) {

        val element = items[position]
        when (viewHolder) {
            is HeaderViewHolder -> viewHolder.bind(element as ItemDetailHeaderViewModel)
            is SwitchViewHolder -> viewHolder.bind(element as ItemDetailSwitchViewModel)
            else -> throw IllegalArgumentException()
        }
    }
    //
    override fun getItemViewType(position: Int): Int {
        val comparable = items[position]
        return when (comparable) {
            is ItemDetailHeaderViewModel -> TYPE_TOPIC
            is ItemDetailSwitchViewModel -> TYPE_SERVER
            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    fun setItems() {
//        items.add(ItemDetailHeaderViewModel("Header abc"))
//        items.add(ItemDetailSwitchViewModel("switch 1"))
//        items.add(ItemDetailSwitchViewModel("switch 2"))
//        items.add(ItemDetailPlusViewModel())

        notifyDataSetChanged()
    }

    inner class ItemViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {


        init {
            containerView.setOnSafeClickListener {
                onItemClick?.invoke()
            }
        }

    }

    inner class HeaderViewHolder(itemView: View) : ItemDetailBaseViewHolder<ItemDetailHeaderViewModel>(itemView) {

        init {
            itemView.setOnSafeClickListener {
                onItemClick?.invoke()
            }
        }

        override fun bind(viewModel: ItemDetailHeaderViewModel) {
            itemView.textView.text = viewModel.title
        }
    }

    inner class SwitchViewHolder(itemView: View) : ItemDetailBaseViewHolder<ItemDetailSwitchViewModel>(itemView) {

        override fun bind(viewModel: ItemDetailSwitchViewModel) {
            itemView.textView.text = viewModel.name
        }
    }

    inner class PlusViewHolder(
        override val containerView: View

    ) : ItemDetailBaseViewHolder<ItemDetailPlusViewModel>(containerView), LayoutContainer {

        init {
            containerView.imageButton.setOnSafeClickListener {
                onItemPlusClick?.invoke("Click Plus")
            }
        }

        override fun bind(viewModel: ItemDetailPlusViewModel) {}
    }

}