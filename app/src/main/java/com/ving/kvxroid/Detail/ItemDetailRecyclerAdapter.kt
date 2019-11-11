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
import kotlinx.android.synthetic.main.activity_item_detail_header.view.textView
import kotlinx.android.synthetic.main.item_detail_chart_view_holder.view.*
import kotlinx.android.synthetic.main.item_detail_plus_view_holder.view.*
import kotlinx.android.synthetic.main.item_detail_switch_view_holder.view.*

class ItemDetailRecyclerAdapter(private val items: ArrayList<AnyObject>): RecyclerView.Adapter<ItemDetailBaseViewHolder<*>>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_SWITCH = 1
        private const val TYPE_PLUS   = 2
        private const val TYPE_TRASH  = 3
        private const val TYPE_CHART  = 4
        private const val TYPE_FOOTER = 5

    }

    private var data: List<Any> = emptyList()


    //    private var listOfMovies = listOf<MovieModel>()
    var onItemClick: (() -> Unit)? = null
    var onItemPlusClick: ((String) -> Unit)? = null
    var onInfoClick: ((String) -> Unit)? = null
    var onTrashClick: ((String) -> Unit)? = null
    var onItemTrashClick: ((String) -> Unit)? = null




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
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_detail_chart_view_holder, parent, false)
                return ChartViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")

        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: ItemDetailBaseViewHolder<*>, position: Int) {

        val element = items[position]
        when (viewHolder) {
            is HeaderViewHolder -> viewHolder.bind(element as ItemDetailHeaderViewModel)
            is SwitchViewHolder -> viewHolder.bind(element as ItemDetailSwitchViewModel)
            is PlusViewHolder ->   viewHolder.bind(element as ItemDetailPlusViewModel)
            is TrashViewHolder ->   viewHolder.bind(element as ItemDetailTrashViewModel)
            is ChartViewHolder ->   viewHolder.bind(element as ItemDetailChartViewModel)

            else -> throw IllegalArgumentException()
        }
    }
//
    override fun getItemViewType(position: Int): Int {
        val comparable = items[position]
        return when (comparable) {
            is ItemDetailHeaderViewModel -> TYPE_HEADER
            is ItemDetailSwitchViewModel -> TYPE_SWITCH
            is ItemDetailPlusViewModel -> TYPE_PLUS
            is ItemDetailTrashViewModel -> TYPE_TRASH
            is ItemDetailChartViewModel -> TYPE_CHART

            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    fun setItems() {
//        items.add(ItemDetailHeaderViewModel("Header abc"))
//        items.add(ItemDetailSwitchViewModel("switch 1"))
//        items.add(ItemDetailSwitchViewModel("switch 2"))
//        items.add(ItemDetailPlusViewModel())

        notifyDataSetChanged()
    }

    inner class ItemViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            containerView.setOnSafeClickListener {
                onItemClick?.invoke()
            }

        }

    }

    inner class HeaderViewHolder(itemView: View) : ItemDetailBaseViewHolder<ItemDetailHeaderViewModel>(itemView) {

        init {
            itemView.setOnSafeClickListener {
                onItemClick?.invoke()
            }
        }

        override fun bind(viewModel: ItemDetailHeaderViewModel) {
            itemView.textView.text = viewModel.title
        }
    }

    inner class SwitchViewHolder(itemView: View) : ItemDetailBaseViewHolder<ItemDetailSwitchViewModel>(itemView) {

        private lateinit var viewModel: ItemDetailSwitchViewModel


        init {

            itemView.btnInfo.setOnSafeClickListener {
                onInfoClick?.invoke("Oninfo")
            }

            itemView.btnTrash.setOnSafeClickListener {
                onTrashClick?.invoke(viewModel.id)
            }
        }

        override fun bind(viewModel: ItemDetailSwitchViewModel) {
            this.viewModel = viewModel
            itemView.textView.text = viewModel.name
        }
    }

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