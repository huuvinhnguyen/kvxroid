package com.ving.kvxroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer


class ItemListGridRecyclerAdapter: RecyclerView.Adapter<ItemListGridRecyclerAdapter.ItemViewHolder>() {
//    private var listOfMovies = listOf<MovieModel>()
    var onItemClick: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_grid_movie, parent, false))
    }



//    override fun getItemCount(): Int = listOfMovies.size
    override fun getItemCount(): Int = 5


    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
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
}