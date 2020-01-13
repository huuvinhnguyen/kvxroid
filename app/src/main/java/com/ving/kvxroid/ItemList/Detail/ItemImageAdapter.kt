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
import kotlinx.android.synthetic.main.item_image_view_holder.view.*


class ItemImageAdapter(
    private val items: ArrayList<AnyObject>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    var onItemClick: ((ViewModel) -> Unit)? = null
    var onItemPlusClick: ((String) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =

        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_image_view_holder, parent, false))


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder:  RecyclerView.ViewHolder, position: Int) {
        val viewModel = items[position] as ViewModel
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

        private lateinit var viewModel: ViewModel

        init {

            containerView.setOnSafeClickListener {
                onItemClick?.invoke(viewModel)
            }

        }

        fun bind(viewModel: ViewModel) {

            this.viewModel = viewModel
            itemView.checkBox.isChecked = viewModel.isSelected

            Glide.with(itemView)  //2
                .load(viewModel.imageUrl) //3
                .centerCrop() //4
                .placeholder(R.drawable.ic_image_place_holder) //5
                .error(R.drawable.ic_broken_image) //6
                .fallback(R.drawable.ic_no_image) //7
                .into(itemView.imageView) //8
        }
    }

    data class ViewModel (
        val id: String,
        val name: String,
        val imageUrl: String,
        var isSelected: Boolean
    ) : AnyObject
}

