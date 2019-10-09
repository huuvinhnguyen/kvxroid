package com.ving.kvxroid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

class ItemDetailRecyclerAdapter: RecyclerView.Adapter<ItemDetailBaseViewHolder<*>>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_CONTENT1 = 1
    }

    //    private var listOfMovies = listOf<MovieModel>()
    var onItemClick: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDetailBaseViewHolder<*> {
        return when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_detail_view_holder, parent, false)
                return HeaderViewHolder(view)
            }
            TYPE_CONTENT1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_detail_view_holder, parent, false)
                return HeaderViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")

        }
    }



    //    override fun getItemCount(): Int = listOfMovies.size
    override fun getItemCount(): Int = 5


    override fun onBindViewHolder(viewHolder: ItemDetailBaseViewHolder<*>, position: Int) {
//        val viewHolder = viewHolder as ItemListViewHolder
//        movieViewHolder.bindView(listOfMovies[position])
//        viewHolder.bindView()
    }

    fun setList() {
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

    inner class HeaderViewHolder(itemView: View) : ItemDetailBaseViewHolder<String>(itemView) {

        override fun bind(item: String) {
        }
    }

    inner class ContentViewHolder(itemView: View) : ItemDetailBaseViewHolder<String>(itemView) {

        override fun bind(item: String) {
        }
    }
}