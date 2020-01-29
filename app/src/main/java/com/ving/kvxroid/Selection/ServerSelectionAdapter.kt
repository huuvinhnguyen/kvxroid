package com.ving.kvxroid.Selection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.R
import com.ving.kvxroid.setOnSafeClickListener
import kotlinx.android.synthetic.main.selection_server_view_holder.view.*
import kotlinx.android.synthetic.main.selection_server_view_holder.view.checkBox


class ServerSelectionAdapter(private val items: ArrayList<AnyObject>) :
    RecyclerView.Adapter<ServerSelectionBaseViewHolder<*>>() {

    companion object {
        private const val TYPE_TYPE = 0
        private const val TYPE_SERVER = 1
    }

    private var data: List<Any> = emptyList()


    //    private var listOfMovies = listOf<MovieModel>()
    var onItemClick: ((ServerSelectionViewModel) -> Unit)? = null
    var onItemPlusClick: ((String) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServerSelectionBaseViewHolder<*> {
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

    override fun onBindViewHolder(viewHolder: ServerSelectionBaseViewHolder<*>, position: Int) {

        val element = items[position]
        when (viewHolder) {
            is SelectionTypeViewHolder -> viewHolder.bind(element as SelectionTypeViewModel)
            is SelectionServerViewHolder -> viewHolder.bind(element as ServerSelectionViewModel)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val comparable = items[position]
        return when (comparable) {
            is SelectionTypeViewModel -> TYPE_TYPE
            is ServerSelectionViewModel -> TYPE_SERVER
            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    fun setItems() {

//        items.add(ServerSelectionViewModel("abc"))
//        items.add(ServerSelectionViewModel("eeee"))

        notifyDataSetChanged()
    }

    inner class SelectionTypeViewHolder(itemView: View) :
        ServerSelectionBaseViewHolder<SelectionTypeViewModel>(itemView) {

        init {
            itemView.setOnSafeClickListener {
//                onItemClick?.invoke()
            }
        }

        override fun bind(item: SelectionTypeViewModel) {
//            itemView.tvType.text = item.title
        }

    }

    inner class SelectionServerViewHolder(itemView: View) :
        ServerSelectionBaseViewHolder<ServerSelectionViewModel>(itemView) {

        private lateinit var viewModel: ServerSelectionViewModel


        init {
            itemView.setOnSafeClickListener {
                onItemClick?.invoke(viewModel)
            }

            itemView.checkBox.setOnSafeClickListener {
                onItemClick?.invoke(viewModel)
            }
        }

        override fun bind(item: ServerSelectionViewModel) {
            this.viewModel = item
//            itemView.checkBox.isChecked = viewModel.isSelected
            itemView.tvServer.text = item.title

        }
    }


}