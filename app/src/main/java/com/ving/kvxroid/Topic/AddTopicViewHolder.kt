package com.ving.kvxroid.Topic

import android.service.autofill.OnClickAction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.R
import com.ving.kvxroid.extensions.empty
import com.ving.kvxroid.extensions.onChange
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_item_name.view.etName
import kotlinx.android.synthetic.main.add_topic_qos_view_holder.view.*
import kotlinx.android.synthetic.main.add_topic_retain_view_holder.view.*
import kotlinx.android.synthetic.main.add_topic_save_view_holder.view.*
import kotlinx.android.synthetic.main.add_topic_view_holder.view.*
import kotlinx.android.synthetic.main.add_topic_switch_view_holder.*


class TopicViewHolder(
    override val containerView: View
) : AddTopicBaseViewHolder<TopicViewModel>(containerView),
    LayoutContainer {

    companion object {
            fun renderView(parent: ViewGroup): TopicViewHolder {

                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.add_topic_view_holder, parent, false)
                return TopicViewHolder(view)
            }
        }


    fun bind(viewModel: TopicViewModel, clickListener:  ((String) -> Unit)?) {
        itemView.etName.setText(viewModel.name)

        itemView.etName.onChange {

            viewModel.name = it
        }

        itemView.etTopic.setText(viewModel.topic)
        itemView.etTopic.onChange {
            viewModel.topic = it
        }

        itemView.etType.setText(viewModel.type)
        itemView.etType.onChange {
            viewModel.type = it
        }

        itemView.btnSelect.setOnClickListener {

            clickListener?.invoke(viewModel.type)
        }

        itemView.inputName.error = String.empty()

        if (viewModel.name.isEmpty()  && viewModel.isErrorChecked) {
            itemView.inputName.error = "Please input information!"
            itemView.inputName.requestFocus()
        }

        itemView.inputTopic.error = String.empty()

        if (viewModel.topic.isEmpty()  && viewModel.isErrorChecked) {
            itemView.inputTopic.error = "Please input information!"
            itemView.inputTopic.requestFocus()

        }
    }

}

data class TopicViewModel(
    var id: String = String.empty(),
    var name: String = String.empty(),
    var topic: String = String.empty(),
    var type: String = String.empty(),
    var isErrorChecked: Boolean = false
) : AnyObject

class TopicSaveViewHolder(
    override val containerView: View
) : AddTopicBaseViewHolder<TopicSaveViewModel>(containerView),
    LayoutContainer {

    companion object {
        fun renderView(parent: ViewGroup): TopicSaveViewHolder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.add_topic_save_view_holder, parent, false)
            return TopicSaveViewHolder(view)
        }
    }


     fun bind(viewModel: TopicSaveViewModel,  clickListener: ((String) -> Unit)?) {
         itemView.btnSave.setOnClickListener { clickListener?.invoke("save topic") }

    }

    fun bind2(viewModel: TopicSaveViewModel, clickListener: ((String) -> Unit)?) = with(itemView)  {
        itemView.btnSave
    }

}

data class TopicSaveViewModel(val title: String = String.empty()) : AnyObject


class TopicQosViewHolder(
    override val containerView: View
) : AddTopicBaseViewHolder<TopicQosViewModel>(containerView),
    LayoutContainer {

    private lateinit var viewModel: TopicQosViewModel

    init {
    }

    companion object {
        fun renderView(parent: ViewGroup): TopicQosViewHolder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.add_topic_qos_view_holder, parent, false)
            return TopicQosViewHolder(view)
        }
    }

    fun bind(viewModel: TopicQosViewModel) {

        this.viewModel = viewModel

        if (viewModel.value == "0") {
            itemView.radio0.isChecked = true
        } else if (viewModel.value == "1") {
            itemView.radio1.isChecked = true

        } else if (viewModel.value == "2") {
            itemView.radio2.isChecked = true
        }

        itemView.radio0.setOnClickListener {
            viewModel.value = "0"

        }
        itemView.radio1.setOnClickListener {
            viewModel.value = "1"

        }

        itemView.radio2.setOnClickListener {
            viewModel.value = "2"
        }

    }

}

data class TopicQosViewModel(var value: String = String.empty()) : AnyObject

class TopicRetainViewHolder(
    override val containerView: View
) : AddTopicBaseViewHolder<TopicRetainViewModel>(containerView), LayoutContainer {
    private lateinit var viewModel: TopicRetainViewModel
    companion object {
        fun renderView(parent: ViewGroup): TopicRetainViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.add_topic_retain_view_holder, parent, false)
            return TopicRetainViewHolder(view)
        }
    }

    fun bind(viewModel: TopicRetainViewModel) {

        this.viewModel = viewModel

        if (viewModel.value == "0") {
            itemView.radioNo.isChecked = true
        } else if (viewModel.value == "1") {
            itemView.radioYes.isChecked = true
        }

        itemView.radioYes.setOnClickListener {
            viewModel.value = "1"
        }

        itemView.radioNo.setOnClickListener {
            viewModel.value = "0"
        }

    }

}

data class TopicRetainViewModel(var value: String = String.empty()): AnyObject


class TopicSwitchViewHolder(
    override val containerView: View
) : AddTopicBaseViewHolder<TopicSwitchViewModel>(containerView),
    LayoutContainer {

    lateinit var viewModel: TopicSwitchViewModel

    companion object {
        fun renderView(parent: ViewGroup): TopicSwitchViewHolder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.add_topic_switch_view_holder, parent, false)
            return TopicSwitchViewHolder(view)
        }
    }

    init {

    }

    fun bind(viewModel: TopicSwitchViewModel) {

//        itemView.btnSelect.setOnClickListener { clickListener?.invoke("abc")  }
//        itemView.btnSelect .setOnClickListener { clickListener?.invoke("abc")}


    }

}
data class TopicSwitchViewModel(val type: String = String.empty()) : AnyObject




