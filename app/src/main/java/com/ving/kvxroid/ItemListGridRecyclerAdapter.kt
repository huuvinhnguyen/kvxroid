package com.ving.kvxroid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class ItemListGridRecyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//    private var listOfMovies = listOf<MovieModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_grid_movie, parent, false))
    }

//    override fun getItemCount(): Int = listOfMovies.size
    override fun getItemCount(): Int = 5


    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = viewHolder as ItemListViewHolder
//        movieViewHolder.bindView(listOfMovies[position])
        viewHolder.bindView()
    }

    fun setList() {
        notifyDataSetChanged()
    }
}