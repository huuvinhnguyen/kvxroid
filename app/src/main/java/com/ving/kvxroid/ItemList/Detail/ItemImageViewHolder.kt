package com.ving.kvxroid.ItemList.Detail

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ving.kvxroid.Models.Item
import kotlinx.android.synthetic.main.list_item_grid_movie.view.*

class ItemImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(viewModel: Item) {
        itemView.textTitle.text = viewModel.name


//        Glide.with(itemView.context).load(movieModel.moviePicture!!).into(itemView.imageMovie)
    }

}