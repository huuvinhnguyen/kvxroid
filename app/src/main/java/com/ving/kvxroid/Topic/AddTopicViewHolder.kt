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

        itemView.btnSelect.setOnClickListener { clickListener?.invoke("abc")}
    }
}

data class TopicViewModel(
    var name: String = String.empty(),
    var topic: String = String.empty(),
    var type: String = String.empty()
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

    companion object {
        fun renderView(parent: ViewGroup): TopicQosViewHolder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.add_topic_qos_view_holder, parent, false)
            return TopicQosViewHolder(view)
        }
    }

    fun bind(viewModel: TopicQosViewModel) {

    }

}

data class TopicQosViewModel(val value: String = String.empty()) : AnyObject


class TopicSwitchViewHolder(
    override val containerView: View
) : AddTopicBaseViewHolder<TopicSwitchViewModel>(containerView),
    LayoutContainer {

    companion object {
        fun renderView(parent: ViewGroup): TopicSwitchViewHolder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.add_topic_switch_view_holder, parent, false)
            return TopicSwitchViewHolder(view)
        }
    }

    fun bind(viewModel: TopicSwitchViewModel) {
//        itemView.btnSelect.setOnClickListener { clickListener?.invoke("abc")  }
//        itemView.btnSelect .setOnClickListener { clickListener?.invoke("abc")}


    }

}
data class TopicSwitchViewModel(val title: String = String.empty()) : AnyObject




