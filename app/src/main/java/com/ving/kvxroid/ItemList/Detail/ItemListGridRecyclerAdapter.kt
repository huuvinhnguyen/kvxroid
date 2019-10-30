package com.ving.kvxroid.ItemList.Detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Detail.ItemDetailPlusViewModel
import com.ving.kvxroid.Detail.ItemDetailRecyclerAdapter
import com.ving.kvxroid.Detail.ItemDetailSwitchViewModel
import com.ving.kvxroid.R
import com.ving.kvxroid.setOnSafeClickListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_detail_plus_view_holder.view.*
import kotlinx.android.synthetic.main.list_item_grid_movie.view.*


class ItemListGridRecyclerAdapter(
    private val items: ArrayList<AnyObject>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_PLUS   = 1

    }

    var onItemClick: (() -> Unit)? = null
    var onItemPlusClick: ((String) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =

        when (viewType) {

            TYPE_ITEM -> {
//                val view = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.item_detail_plus_view_holder, parent, false)
//                PlusViewHolder(view)

                 ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_grid_movie, parent, false))
            }

            TYPE_PLUS -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_detail_plus_view_holder, parent, false)
                 PlusViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }



    override fun getItemViewType(position: Int): Int {
        val comparable = items[position]
        return when (comparable) {
            is ItemViewModel -> TYPE_ITEM
            is ItemDetailPlusViewModel -> TYPE_PLUS
            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder:  RecyclerView.ViewHolder, position: Int) {

         when (viewHolder) {
            is ItemViewHolder -> {
                val viewModel = items[position] as ItemViewModel
                viewHolder.bindView(viewModel)
            }
        }
    }

    fun setList() {
        notifyDataSetChanged()
    }


    inner class ItemViewHolder(
        override val containerView: View
    ):  RecyclerView.ViewHolder(containerView), LayoutContainer  {

        fun bindView(viewModel: ItemViewModel) {
            itemView.textTitle.text = viewModel.name

        }

        init {

            containerView.setOnSafeClickListener {
                onItemClick?.invoke()
            }
        }
    }

    inner class PlusViewHolder(
        override val containerView: View
    ):  RecyclerView.ViewHolder(containerView), LayoutContainer  {

        init {

            containerView.imageButton.setOnSafeClickListener {
                onItemPlusClick?.invoke("Click Plus")
            }
        }
    }
}

