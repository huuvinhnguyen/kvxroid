package com.ving.kvxroid.Redux
import com.ving.kvxroid.AnyObject
import com.ving.kvxroid.Detail.ItemTopicViewModel
import com.ving.kvxroid.ItemList.Detail.ItemNameViewModel
import com.ving.kvxroid.ItemList.Detail.ItemViewModel
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
data class TopicListLoadAction(val unit: Unit = Unit): Action
data class TopicListConnectAction(val unit: Unit = Unit): Action
data class TopicListUpdateAction(val unit: Unit = Unit): Action
data class TopicListAddAction(val unit: Unit = Unit): Action
data class TopicListGetItemAction(val unit: Unit = Unit): Action

data class ItemActionAdd(val unit: Unit = Unit): Action {
    var name: String? = null
}

data class ItemActionRemove(val unit: Unit = Unit): Action {
    var id: String = ""
}
data class ItemNameActionLoad(val unit: Unit = Unit): Action {
    var itemNameViewModel: ItemNameViewModel? = null
}
data class ItemListAction(val unit: Unit = Unit): Action
data class ItemLoadAction(val unit: Unit = Unit): Action {
    var itemViewModel: ItemViewModel? = null
}
data class ItemUpdateAction(val unit: Unit = Unit): Action {
    var itemViewModel: ItemViewModel? = null
}
data class ItemImageActionLoad(val unit: Unit = Unit): Action
data class ItemImageActionFetch(val unit: Unit = Unit): Action {
    var list: ArrayList<AnyObject> = ArrayList()
}
data class ItemImageActionSelect(val unit: Unit = Unit): Action {
    var id: String = ""
}


