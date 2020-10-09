package com.ving.kvxroid.Detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ving.kvxroid.R
import com.ving.kvxroid.setOnSafeClickListener
import kotlinx.android.extensions.LayoutContainer
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Common.hideKeyboard
import com.ving.kvxroid.extensions.empty
import com.ving.kvxroid.extensions.onChange
import kotlinx.android.synthetic.main.activity_item_detail_header.view.textView
import kotlinx.android.synthetic.main.item_detail_gauge_view_holder.view.*
import kotlinx.android.synthetic.main.topic_detail_gauge_view_holder.view.*
import kotlinx.android.synthetic.main.topic_detail_gauge_view_holder.view.tvName
import kotlinx.android.synthetic.main.item_detail_number_view_holder.view.*
import kotlinx.android.synthetic.main.item_detail_plus_view_holder.view.*
import kotlinx.android.synthetic.main.item_detail_switch_view_holder.view.*
import kotlinx.android.synthetic.main.item_detail_switch_view_holder.view.btnInfo
import kotlinx.android.synthetic.main.item_detail_switch_view_holder.view.tvValue
import kotlinx.android.synthetic.main.item_detail_switch_view_holder.view.tvTime

import kotlinx.android.synthetic.main.item_detail_value_view_holder.view.*
import kotlinx.android.synthetic.main.list_item_grid_movie.view.*
import kotlinx.android.synthetic.main.list_item_grid_movie.view.imageView

class ItemDetailAdapter(private val items: ArrayList<AnyObject>): RecyclerView.Adapter<ItemDetailBaseViewHolder<*>>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_SWITCH = 1
        private const val TYPE_PLUS   = 2
        private const val TYPE_TRASH  = 3
        private const val TYPE_CHART  = 4
        private const val TYPE_CHART_LINE = 5
        private const val TYPE_FOOTER = 6
        private const val TYPE_VALUE = 7
        private const val TYPE_NUMBER = 8
        private const val TYPE_GAUGE = 9

    }


    var onItemClick: (() -> Unit)? = null
    var onItemPlusClick: ((String) -> Unit)? = null
    var onInfoClick: ((String) -> Unit)? = null
    var onTrashClick: ((String) -> Unit)? = null
    var onItemTrashClick: ((String) -> Unit)? = null
    var onItemEditClick: ((String) -> Unit)? = null
    var onSwitchClick: ((String, String) -> Unit)? = null
    var onSendClick: ((String, String) -> Unit)? = null


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

            TYPE_VALUE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_detail_value_view_holder, parent, false)
                return ValueViewHolder(view)
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

            TYPE_NUMBER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_detail_number_view_holder, parent, false)
                return NumberViewHolder(view)

            }

            TYPE_GAUGE -> {

                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_detail_gauge_view_holder, parent, false)
                return GaugeViewHolder(view)

            }


            else -> throw IllegalArgumentException("Invalid view type")

        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: ItemDetailBaseViewHolder<*>, position: Int) {

        val element = items[position]
        when (viewHolder) {
            is HeaderViewHolder -> viewHolder.bind(element as ItemDetailHeaderViewModel)
            is SwitchViewHolder -> viewHolder.bind(element as SwitchViewModel)
            is ValueViewHolder -> viewHolder.bind(element as ValueViewModel)
            is PlusViewHolder ->   viewHolder.bind(element as ItemDetailPlusViewModel)
            is TrashViewHolder ->   viewHolder.bind(element as ItemDetailTrashViewModel)
            is ChartViewHolder ->   viewHolder.bind(element as ItemDetailChartViewModel)
            is ChartLineViewHolder -> viewHolder.bind(element as ItemLineChartViewModel)
            is NumberViewHolder -> viewHolder.bind(element as NumberViewModel)
            is GaugeViewHolder -> viewHolder.bind(element as GaugeViewModel)

            else -> throw IllegalArgumentException()
        }
    }
//
    override fun getItemViewType(position: Int): Int {
        val comparable = items[position]
        return when (comparable) {
            is ItemDetailHeaderViewModel -> TYPE_HEADER
            is SwitchViewModel -> TYPE_SWITCH
            is ValueViewModel -> TYPE_VALUE
            is ItemDetailPlusViewModel -> TYPE_PLUS
            is ItemDetailTrashViewModel -> TYPE_TRASH
            is ItemDetailChartViewModel -> TYPE_CHART
            is ItemLineChartViewModel -> TYPE_CHART_LINE
            is NumberViewModel -> TYPE_NUMBER
            is GaugeViewModel -> TYPE_GAUGE

            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    fun setItems(list: ArrayList<AnyObject>) {
        items.clear()
        items.addAll(list)
        items.add(ItemDetailPlusViewModel())
        items.add(ItemDetailTrashViewModel())
//        items.add(ValueViewModel())
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
            Glide.with(itemView)  //2
                .load(viewModel.imageUrl) //3
                .centerCrop() //4
                .placeholder(R.drawable.ic_image_place_holder) //5
                .error(R.drawable.ic_broken_image) //6
                .fallback(R.drawable.ic_no_image) //7
                .circleCrop()
                .into(itemView.imageView) //8

        }
    }

    inner class SwitchViewHolder(itemView: View) : ItemDetailBaseViewHolder<SwitchViewModel>(itemView) {

        private lateinit var viewModel: SwitchViewModel

        init {

            itemView.btnInfo.setOnSafeClickListener {
                onInfoClick?.invoke(viewModel.id)
            }

            itemView.btnSwitch.setOnSafeClickListener {
                val message = if (viewModel.value == "1") "0" else "1"
                val topicId = viewModel.id
                onSwitchClick?.invoke(topicId, message)
            }

        }

        override fun bind(viewModel: SwitchViewModel) {
            this.viewModel = viewModel
            itemView.textView.text = viewModel.name
            itemView.tvValue.text = viewModel.value
            itemView.tvTime.text = viewModel.time
        }
    }

    data class SwitchViewModel(
        val id: String = String.empty(),
        val name: String = String.empty(),
        val value: String = String.empty(),
        val time: String = String.empty()
    ) : AnyObject


    inner class NumberViewHolder(itemView: View) : ItemDetailBaseViewHolder<NumberViewModel>(itemView) {

        private lateinit var viewModel: NumberViewModel

        init {

            itemView.btnInfo.setOnSafeClickListener {
                onInfoClick?.invoke(viewModel.id)
            }
        }

        override fun bind(viewModel: NumberViewModel) {
            this.viewModel = viewModel
            itemView.tvName.text = viewModel.name
            itemView.tvNumberValue.text = if (viewModel.value.isEmpty()) "0"  else viewModel.value
            itemView.tvTime.text = viewModel.time
        }
    }

    data class NumberViewModel(
        val id: String = String.empty(),
        val name: String = String.empty(),
        var value: String = String.empty(),
        var time: String = String.empty()
    ) : AnyObject


    inner class ValueViewHolder(itemView: View) : ItemDetailBaseViewHolder<ValueViewModel>(itemView) {

        init {

            itemView.btnInfo.setOnSafeClickListener {
                onInfoClick?.invoke(viewModel.id)
            }

            itemView.btnSend.setOnSafeClickListener {
                val topicId = viewModel.id
                onSendClick?.invoke(topicId, viewModel.message)
                itemView.etMessage.hideKeyboard()

            }

        }

        private lateinit var viewModel: ValueViewModel

        override fun bind(viewModel: ValueViewModel) {
            this.viewModel = viewModel
            itemView.etMessage.onChange {
                viewModel.message = it
            }
            itemView.tvName.text = viewModel.name
            itemView.tvTime.text = viewModel.time
        }
    }

    data class ValueViewModel(
        val id: String = String.empty(),
        val name: String = String.empty(),
        var value: String = String.empty(),
        var message: String = String.empty(),
        var time: String = String.empty()
    ) : AnyObject

    inner class GaugeViewHolder(itemView: View) : ItemDetailBaseViewHolder<GaugeViewModel>(itemView) {

        init {

            itemView.setOnSafeClickListener {
                onInfoClick?.invoke(viewModel.id)
            }


        }

        private lateinit var viewModel: GaugeViewModel

        override fun bind(viewModel: GaugeViewModel) {
            this.viewModel = viewModel
//            itemView.gaugeView.unit = "°C"
            val value = viewModel.value?.toFloatOrNull() ?: 0F
//            itemView.gaugeView.speedTo(value, 4000)
            itemView.tvName.text = viewModel.name
            itemView.tvValue.text = if (viewModel.value.isEmpty()) "0"  else viewModel.value


        }
    }

    data class GaugeViewModel(
        val id: String = String.empty(),
        val name: String = String.empty(),
        var value: String = String.empty(),
        var message: String = String.empty()
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

