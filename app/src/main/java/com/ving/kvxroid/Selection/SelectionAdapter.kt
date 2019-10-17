package com.ving.kvxroid.Selection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Detail.ItemDetailBaseViewHolder
import com.ving.kvxroid.Detail.ItemDetailHeaderViewModel
import com.ving.kvxroid.Detail.ItemDetailPlusViewModel
import com.ving.kvxroid.Detail.ItemDetailSwitchViewModel
import com.ving.kvxroid.R
import com.ving.kvxroid.setOnSafeClickListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_item_detail_header.view.*
import kotlinx.android.synthetic.main.item_detail_plus_view_holder.view.*

class SelectionAdapter(private val items: ArrayList<AnyObject>) :
    RecyclerView.Adapter<SelectionBaseViewHolder<*>>() {

    companion object {
        private const val TYPE_TYPE = 0
        private const val TYPE_SERVER = 1


    }

    private var data: List<Any> = emptyList()


    //    private var listOfMovies = listOf<MovieModel>()
    var onItemClick: (() -> Unit)? = null
    var onItemPlusClick: ((String) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionBaseViewHolder<*> {
        return when (viewType) {
            TYPE_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.selection_type_view_holder, parent, false)
                return SelectionTypeViewHolder(view)
            }
            TYPE_SERVER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.selection_server_view_holder, parent, false)
                return SelectionServerViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")

        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: SelectionBaseViewHolder<*>, position: Int) {

        val element = items[position]
        when (viewHolder) {
            is SelectionTypeViewHolder -> viewHolder.bind(element as SelectionTypeViewModel)
            is SelectionServerViewHolder -> viewHolder.bind(element as SelectionServerViewModel)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val comparable = items[position]
        return when (comparable) {
            is SelectionTypeViewModel -> TYPE_TYPE
            is SelectionServerViewModel -> TYPE_SERVER
            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    fun setItems() {
        items.add(SelectionTypeViewModel("Type here"))
        items.add(SelectionServerViewModel("Server here"))


        notifyDataSetChanged()
    }

    inner class SelectionTypeViewHolder(view: View) :
        SelectionBaseViewHolder<SelectionTypeViewModel>(view) {

        init {
            view.setOnSafeClickListener {
                onItemClick?.invoke()
            }
        }

        override fun bind(item: SelectionTypeViewModel) {


        }

    }

    inner class SelectionServerViewHolder(view: View) :
        SelectionBaseViewHolder<SelectionServerViewModel>(view) {

        init {
            view.setOnSafeClickListener {
                onItemClick?.invoke()
            }
        }

        override fun bind(item: SelectionServerViewModel) {


        }
    }


}