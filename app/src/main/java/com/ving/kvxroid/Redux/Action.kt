package com.ving.kvxroid.Redux
import org.rekotlin.Action

data class CounterActionIncrease(val unit: Unit = Unit): Action
data class CounterActionDecrease(val unit: Unit = Unit): Action
data class ItemListStateLoad(val unit: Unit = Unit): Action
data class ItemDetailLoad(val unit: Unit = Unit): Action
data class ConnectionActionAdd(val unit: Unit = Unit): Action
data class ConnectionActionLoad(val unit: Unit = Unit): Action
data class TopicActionAdd(val unit: Unit = Unit): Action
data class TopicActionConnect(val unit: Unit = Unit): Action
data class TopicActionUpdate(val unit: Unit = Unit): Action
data class ItemActionAdd(val unit: Unit = Unit): Action {
    var name: String? = null
}
data class ItemActionLoad(val unit: Unit = Unit): Action