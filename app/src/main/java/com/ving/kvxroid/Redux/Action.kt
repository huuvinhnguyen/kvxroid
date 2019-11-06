package com.ving.kvxroid.Redux
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Detail.ItemTopicViewModel
import com.ving.kvxroid.ItemList.Detail.ItemNameViewModel
import org.rekotlin.Action

data class CounterActionIncrease(val unit: Unit = Unit): Action
data class CounterActionDecrease(val unit: Unit = Unit): Action
data class ConnectionActionAdd(val unit: Unit = Unit): Action
data class ConnectionActionLoad(val unit: Unit = Unit): Action
data class TopicActionAdd(val unit: Unit = Unit): Action {
    var viewModel: ItemTopicViewModel? = null
}
data class TopicActionRemove(val unit: Unit = Unit): Action {
    var id: String = ""
}
data class TopicActionLoad(val unit: Unit = Unit): Action
data class TopicActionConnect(val unit: Unit = Unit): Action
data class TopicActionUpdate(val unit: Unit = Unit): Action
data class ItemActionAdd(val unit: Unit = Unit): Action {
    var name: String? = null
}

data class ItemActionRemove(val unit: Unit = Unit): Action {
    var id: String = ""
}
data class ItemNameActionLoad(val unit: Unit = Unit): Action {
    var itemNameViewModel: ItemNameViewModel? = null
}
data class ItemActionLoad(val unit: Unit = Unit): Action
data class ItemImageActionLoad(val unit: Unit = Unit): Action
data class ItemImageActionFetch(val unit: Unit = Unit): Action {
    var list: ArrayList<AnyObject> = ArrayList()
}
data class ItemImageActionSelect(val unit: Unit = Unit): Action {
    var id: String = ""
}


