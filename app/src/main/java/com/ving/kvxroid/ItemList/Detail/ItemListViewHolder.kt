package com.ving.kvxroid.ItemList.Detail


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_grid_movie.view.*

import java.util.*

class ItemListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(viewModel: ItemViewModel) {
        itemView.textTitle.text = viewModel.name


//        Glide.with(itemView.context).load(movieModel.moviePicture!!).into(itemView.imageMovie)
    }

}