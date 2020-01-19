package com.ving.kvxroid.ItemList.Detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Detail.ItemDetailPlusViewModel
import com.ving.kvxroid.Models.Item
import com.ving.kvxroid.R
import com.ving.kvxroid.setOnSafeClickListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_detail_plus_view_holder.view.*
import kotlinx.android.synthetic.main.list_item_grid_movie.view.*
import kotlinx.android.synthetic.main.list_item_grid_movie.view.imageView


class ItemListAdapter(
    private val items: ArrayList<AnyObject>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_PLUS   = 1

    }

    var onItemClick: ((String) -> Unit)? = null
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
            else -> throw IllegalArgumentException("Invalid view kind")
        }



    override fun getItemViewType(position: Int): Int {
        val comparable = items[position]
        return when (comparable) {
            is Item -> TYPE_ITEM
            is ItemDetailPlusViewModel -> TYPE_PLUS
            else -> throw IllegalArgumentException("Invalid kind of data " + position)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder:  RecyclerView.ViewHolder, position: Int) {

         when (viewHolder) {
            is ItemViewHolder -> {
                val viewModel = items[position] as Item
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

        private lateinit var viewModel: Item

        fun bindView(viewModel: Item) {
            this.viewModel = viewModel
            itemView.textTitle.text = viewModel.name

            Glide.with(itemView)  //2
                .load(viewModel.imageUrl) //3
                .centerCrop() //4
                .placeholder(R.drawable.ic_image_place_holder) //5
                .error(R.drawable.ic_broken_image) //6
                .fallback(R.drawable.ic_no_image) //7
                .into(itemView.imageView) //8

        }

        init {

            containerView.setOnSafeClickListener {
                onItemClick?.invoke(viewModel.id)
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

    data class ViewModel (
        val id: String,
        val name: String,
        val imageUrl: String,
        var isSelected: Boolean
    ) : AnyObject

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

}

