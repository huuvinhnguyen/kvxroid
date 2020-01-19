package com.ving.kvxroid.Detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.ving.kvxroid.R
import com.ving.kvxroid.setOnSafeClickListener
import kotlinx.android.extensions.LayoutContainer
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.extensions.empty
import kotlinx.android.synthetic.main.activity_item_detail_header.view.textView
import kotlinx.android.synthetic.main.item_detail_chart_view_holder.view.*
import kotlinx.android.synthetic.main.item_detail_plus_view_holder.view.*
import kotlinx.android.synthetic.main.item_detail_switch_view_holder.view.*

class ItemDetailAdapter(private val items: ArrayList<AnyObject>): RecyclerView.Adapter<ItemDetailBaseViewHolder<*>>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_SWITCH = 1
        private const val TYPE_PLUS   = 2
        private const val TYPE_TRASH  = 3
        private const val TYPE_CHART  = 4
        private const val TYPE_CHART_LINE = 5
        private const val TYPE_FOOTER = 6

    }


    var onItemClick: (() -> Unit)? = null
    var onItemPlusClick: ((String) -> Unit)? = null
    var onInfoClick: ((String) -> Unit)? = null
    var onTrashClick: ((String) -> Unit)? = null
    var onItemTrashClick: ((String) -> Unit)? = null
    var onItemEditClick: ((String) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDetailBaseViewHolder<*> {
        return when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_detail_header_view_holder, parent, false)
                return HeaderViewHolder(view)
            }
            TYPE_SWITCH -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_detail_switch_view_holder, parent, false)
                return SwitchViewHolder(view)
            }
            TYPE_PLUS -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_detail_plus_view_holder, parent, false)
                return PlusViewHolder(view)
            }

            TYPE_TRASH -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_detail_trash_view_holder, parent, false)
                return TrashViewHolder(view)
            }

            TYPE_CHART -> {
                ChartViewHolder.renderView(parent)
            }

            TYPE_CHART_LINE -> {
                ChartLineViewHolder.renderView(parent)
            }

            else -> throw IllegalArgumentException("Invalid view kind")

        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: ItemDetailBaseViewHolder<*>, position: Int) {

        val element = items[position]
        when (viewHolder) {
            is HeaderViewHolder -> viewHolder.bind(element as ItemDetailHeaderViewModel)
            is SwitchViewHolder -> viewHolder.bind(element as SwitchViewModel)
            is PlusViewHolder ->   viewHolder.bind(element as ItemDetailPlusViewModel)
            is TrashViewHolder ->   viewHolder.bind(element as ItemDetailTrashViewModel)
            is ChartViewHolder ->   viewHolder.bind(element as ItemDetailChartViewModel)
            is ChartLineViewHolder -> viewHolder.bind(element as ItemLineChartViewModel)

            else -> throw IllegalArgumentException()
        }
    }
//
    override fun getItemViewType(position: Int): Int {
        val comparable = items[position]
        return when (comparable) {
            is ItemDetailHeaderViewModel -> TYPE_HEADER
            is SwitchViewModel -> TYPE_SWITCH
            is ItemDetailPlusViewModel -> TYPE_PLUS
            is ItemDetailTrashViewModel -> TYPE_TRASH
            is ItemDetailChartViewModel -> TYPE_CHART
            is ItemLineChartViewModel -> TYPE_CHART_LINE


            else -> throw IllegalArgumentException("Invalid kind of data " + position)
        }
    }

    fun setItems() {
        items.add(ItemDetailPlusViewModel())
        items.add(ItemDetailTrashViewModel())
        notifyDataSetChanged()
    }

    inner class HeaderViewHolder(
        override val containerView: View) : ItemDetailBaseViewHolder<ItemDetailHeaderViewModel>(containerView), LayoutContainer {

        init {
            containerView.imageButton.setOnSafeClickListener {
                onItemEditClick?.invoke("Edit")
            }
        }

        override fun bind(viewModel: ItemDetailHeaderViewModel) {
            itemView.textView.text = viewModel.title
        }
    }

    inner class SwitchViewHolder(itemView: View) : ItemDetailBaseViewHolder<SwitchViewModel>(itemView) {

        private lateinit var viewModel: SwitchViewModel

        init {

            itemView.btnInfo.setOnSafeClickListener {
                onInfoClick?.invoke("Oninfo")
            }

            itemView.btnTrash.setOnSafeClickListener {
                onTrashClick?.invoke(viewModel.id)
            }
        }

        override fun bind(viewModel: SwitchViewModel) {
            this.viewModel = viewModel
            itemView.textView.text = viewModel.name
        }
    }

    data class SwitchViewModel(
        val id: String = String.empty(),
        val name: String = String.empty(),
        val value: String = String.empty()
    ) : AnyObject

    inner class PlusViewHolder(
        override val containerView: View

    ) : ItemDetailBaseViewHolder<ItemDetailPlusViewModel>(containerView), LayoutContainer {

        init {
            containerView.imageButton.setOnSafeClickListener {
                onItemPlusClick?.invoke("Click Plus")
            }
        }

        override fun bind(viewModel: ItemDetailPlusViewModel) {}
    }

    inner class TrashViewHolder(
        override val containerView: View

    ) : ItemDetailBaseViewHolder<ItemDetailTrashViewModel>(containerView), LayoutContainer {

        init {
            containerView.imageButton.setOnSafeClickListener {
                onItemTrashClick?.invoke("Trash")
            }
        }

        override fun bind(viewModel: ItemDetailTrashViewModel) {
        }
    }
}