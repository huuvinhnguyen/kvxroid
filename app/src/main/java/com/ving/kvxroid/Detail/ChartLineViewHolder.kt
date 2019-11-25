package com.ving.kvxroid.Detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.ving.kvxroid.R
import kotlinx.android.synthetic.main.item_detail_chart_line_view_holder.view.*
import kotlinx.android.synthetic.main.item_detail_chart_view_holder.view.*




class ChartLineViewHolder(itemView: View) :
    ItemDetailBaseViewHolder<ItemLineChartViewModel>(itemView) {


    private lateinit var viewModel: ItemLineChartViewModel

    companion object {
        fun renderView(parent: ViewGroup): ChartLineViewHolder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_detail_chart_line_view_holder, parent, false)
            return ChartLineViewHolder(view)
        }
    }


    override fun bind(viewModel: ItemLineChartViewModel) {

        configureChart()

    }


    fun getBarData(): ArrayList<ILineDataSet> {
//        val entries = ArrayList<BaseEntry>().apply {
//            add(BarEntry(1f, 60f))
//            add(BarEntry(2f, 80f))
//            add(BarEntry(3f, 70f))
//        }

        val entries = ArrayList<Entry>()

        for (index in 0 until 15)
            entries.add(Entry(index + 0.5f, 5f))

        val dataSet = LineDataSet(entries, "bar").apply {
            //                valueFormatter = IValueFormatter { value, _, _, _ -> "" + value.toInt() }
            isHighlightEnabled = false
            setColors(
                intArrayOf(
                    com.ving.kvxroid.R.color.material_blue,
                    com.ving.kvxroid.R.color.material_green,
                    com.ving.kvxroid.R.color.material_yellow
                ), itemView.context
            )
        }

        val bars = ArrayList<ILineDataSet>()
        bars.add(dataSet)
        return bars
    }
}

fun ChartLineViewHolder.configureChart() {
    val chart = itemView.line_chart
    chart.data = LineData(getBarData())

    chart.axisLeft.apply {
        axisMinimum = 0f
        axisMaximum = 100f
        labelCount = 5
        setDrawTopYLabelEntry(true)
//                setValueFormatter { value, axis -> "" + value.toInt()}
    }

    chart.axisRight.apply {
        setDrawLabels(false)
        setDrawGridLines(false)
        setDrawZeroLine(false)
        setDrawTopYLabelEntry(true)
    }

    val labels = arrayOf("", "label1", "label2", "label3")
    chart.xAxis.apply {
        valueFormatter = IndexAxisValueFormatter(labels)
        labelCount = 3
        position = XAxis.XAxisPosition.BOTTOM
        setDrawLabels(true)
        setDrawGridLines(false)
        setDrawAxisLine(true)
    }

    chart.apply {
//        setDrawValueAboveBar(true)
        description.isEnabled = false
        isClickable = false
        legend.isEnabled = false
        setScaleEnabled(false)
//                animateY(1200, Easing.EasingOption.Linear)
    }

}



