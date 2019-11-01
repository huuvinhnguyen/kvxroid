package com.ving.kvxroid.ItemList.Detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.R
import com.ving.kvxroid.setOnSafeClickListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_detail_plus_view_holder.view.*
import kotlinx.android.synthetic.main.list_item_grid_movie.view.*


class ItemImageAdapter(
    private val items: ArrayList<AnyObject>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    var onItemClick: (() -> Unit)? = null
    var onItemPlusClick: ((String) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =

        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_grid_movie, parent, false))


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder:  RecyclerView.ViewHolder, position: Int) {
        val viewModel = items[position] as ItemImageViewModel
        val view = viewHolder as ItemViewHolder
        view.bind(viewModel)

    }

    fun setList(list: ArrayList<AnyObject>) {
        items.addAll(list)
        notifyDataSetChanged()
    }


    inner class ItemViewHolder(
        override val containerView: View
    ):  RecyclerView.ViewHolder(containerView), LayoutContainer  {

        init {

            containerView.setOnSafeClickListener {
                onItemClick?.invoke()
            }
        }

        fun bind(viewModel: ItemImageViewModel) {

            Glide.with(itemView)  //2
                .load(viewModel.imageUrl) //3
                .centerCrop() //4
                .placeholder(R.drawable.ic_image_place_holder) //5
                .error(R.drawable.ic_broken_image) //6
                .fallback(R.drawable.ic_no_image) //7
                .into(itemView.imageView) //8
        }
    }
}

