package com.ving.kvxroid.Topic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.R
import com.ving.kvxroid.extensions.empty
import com.ving.kvxroid.extensions.onChange
import com.ving.kvxroid.setOnSafeClickListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.add_server_footer_view_holder.view.*
import kotlinx.android.synthetic.main.add_server_view_holder.view.*

abstract class AddServerBaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}

class AddServerAdapter(private val items: ArrayList<AnyObject>): RecyclerView.Adapter<AddServerBaseViewHolder<*>>() {

    companion object {
        private const val TYPE_SERVER = 0
        private const val TYPE_FOOTER = 1
    }

    var onSaveClick: (() -> Unit)? = null
    var onSelectClick: (() -> Unit)? = null
    var onTrashClick: ((String) -> Unit)? = null


    override fun onBindViewHolder(holder: AddServerBaseViewHolder<*>, position: Int) {
        val element = items[position]
        when (holder) {
            is ServerViewHolder -> holder.bind(element as ServerViewModel)
            is ServerFooterViewHolder -> holder.bind(element as ServerFooterViewModel)

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

            TYPE_FOOTER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.add_server_footer_view_holder, parent, false)
                return ServerFooterViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val comparable = items[position]
        return when (comparable) {
            is ServerViewModel -> TYPE_SERVER
            is ServerFooterViewModel -> TYPE_FOOTER

            else -> throw IllegalArgumentException("Invalid type of data " + position)
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
            itemView.etName.setText(item.name)
            itemView.etName.onChange {
                item.name = it
            }

            itemView.etUser.setText(item.user)
            itemView.etUser.onChange {
                item.user = it
            }

            itemView.etPassword.setText(item.password)
            itemView.etPassword.onChange {
                item.password = it
            }

            itemView.etServer.setText(item.server)
            itemView.etServer.onChange {
                item.server = it
            }

            itemView.etPort.setText(item.port)
            itemView.etPort.onChange {
                item.port = it
            }

            itemView.etSslPort.setText(item.sslPort)
            itemView.etSslPort.onChange {
                item.port = it
            }

        }
    }


    data class ServerViewModel(
        var name: String = String.empty(),
        var user: String = String.empty(),
        var password: String = String.empty(),
        var server: String = String.empty(),
        var port: String = String.empty(),
        var sslPort: String = String.empty()

    ) : AnyObject

    inner class ServerFooterViewHolder(
        override val containerView: View
    ) : AddServerBaseViewHolder<ServerFooterViewModel>(containerView),
        LayoutContainer {

        private lateinit var viewModel: ServerFooterViewModel

        init {
            itemView.ibTrash.setOnSafeClickListener { onTrashClick?.invoke(viewModel.id) }
        }

        override fun bind(item: ServerFooterViewModel) {
            this.viewModel = item
        }
    }

    data class ServerFooterViewModel(
        var id: String = String.empty()
    ) : AnyObject

}