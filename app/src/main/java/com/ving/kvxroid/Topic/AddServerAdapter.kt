package com.ving.kvxroid.Topic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.R
import com.ving.kvxroid.extensions.empty
import com.ving.kvxroid.setOnSafeClickListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.add_server_view_holder.view.*

abstract class AddServerBaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}

class AddServerAdapter(private val items: ArrayList<AnyObject>): RecyclerView.Adapter<AddServerBaseViewHolder<*>>() {

    companion object {
        private const val TYPE_SERVER = 0
    }

    var onSaveClick: (() -> Unit)? = null
    var onSelectClick: (() -> Unit)? = null


    override fun onBindViewHolder(holder: AddServerBaseViewHolder<*>, position: Int) {
        val element = items[position]
        when (holder) {
                is ServerViewHolder -> holder.bind(element as ServerViewModel)

            else -> throw IllegalArgumentException()
        }

    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddServerBaseViewHolder<*> {

        return return when (viewType) {
            TYPE_SERVER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.add_server_view_holder, parent, false)
                return ServerViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view kind")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val comparable = items[position]
        return when (comparable) {
            is ServerViewModel -> TYPE_SERVER

            else -> throw IllegalArgumentException("Invalid kind of data " + position)
        }
    }

    fun setItems() {

        notifyDataSetChanged()
    }

    inner class ServerViewHolder(
        override val containerView: View
    ) : AddServerBaseViewHolder<ServerViewModel>(containerView),
        LayoutContainer {

        private lateinit var viewModel: ServerViewModel

        init {
            itemView.btnSave.setOnSafeClickListener { onSaveClick?.invoke() }
            itemView.btnSelect.setOnSafeClickListener { onSelectClick?.invoke() }
        }


        override fun bind(item: ServerViewModel) {
//            this.viewModel = viewModel


        }
    }


    data class ServerViewModel(
        var name: String = String.empty()
    ) : AnyObject

}