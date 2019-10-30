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


class ItemImageAdapter(
    private val items: ArrayList<AnyObject>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    var onItemClick: (() -> Unit)? = null
    var onItemPlusClick: ((String) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =

        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_grid_movie, parent, false))


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder:  RecyclerView.ViewHolder, position: Int) {

    }

    fun setList() {
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
    }
}

