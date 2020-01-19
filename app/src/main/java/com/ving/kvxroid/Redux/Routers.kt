package com.ving.kvxroid.Redux

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import com.ving.kvxroid.Detail.ItemDetailActivity
import com.ving.kvxroid.ItemList.Detail.ItemNameActivity
import com.ving.kvxroid.ItemList.Detail.MainActivity
import org.rekotlinrouter.Routable
import org.rekotlinrouter.RouteElementIdentifier
import org.rekotlinrouter.RoutingCompletionHandler

val itemActivityRoute: RouteElementIdentifier = "ItemActivity"
val itemDetailActivityRoute: RouteElementIdentifier = "ItemDetailActivity"
val itemNameActivityRoute: RouteElementIdentifier = "ItemNameActivity"



object RoutableHelper {

    fun createItemActivityRoutable(context: Context): ItemActivityRoutable {
        val intent = Intent(context, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        return ItemActivityRoutable(context)
    }


    fun createItemDetailActivityRoutable(context: Context): ItemDetailActivityRoutable {
        val intent = Intent(context, ItemDetailActivity::class.java)
        val route = mainStore.state.navigationState.route
        val id = mainStore.state.navigationState.getRouteSpecificState<String>(route)
        intent.putExtra("ITEM_ID", id)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        return ItemDetailActivityRoutable(context)
    }

    fun createItemNameActivity(context: Context): ItemNameActivityRoutable {
        val intent = Intent(context, ItemNameActivity::class.java)

//        val route = mainStore.state.navigationState.route
//        val id = mainStore.state.navigationState.getRouteSpecificState<String>(route) ?: ""
//        intent.putExtra("ITEM_ID", id)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        return ItemNameActivityRoutable(context)
    }
}

class RootRoutable(val context: Context): Routable {
    override fun pushRouteSegment(routeElementIdentifier: RouteElementIdentifier, animated: Boolean, completionHandler: RoutingCompletionHandler): Routable {

        return ItemActivityRoutable(context)

    }

    override fun popRouteSegment(routeElementIdentifier: RouteElementIdentifier, animated: Boolean, completionHandler: RoutingCompletionHandler) {
        TODO("not implemented")
    }

    override fun changeRouteSegment(from: RouteElementIdentifier, to: RouteElementIdentifier, animated: Boolean, completionHandler: RoutingCompletionHandler): Routable {
        TODO("not implemented")
    }
}

class ItemActivityRoutable(val context: Context) : Routable {

    override fun pushRouteSegment(routeElementIdentifier: RouteElementIdentifier, animated: Boolean, completionHandler: RoutingCompletionHandler): Routable {

        if(routeElementIdentifier == itemDetailActivityRoute){
            return RoutableHelper.createItemDetailActivityRoutable(context)
        }

        if(routeElementIdentifier == itemNameActivityRoute){
            return RoutableHelper.createItemNameActivity(context)
        }

        return ItemDetailActivityRoutable(context)
    }

    override fun popRouteSegment(routeElementIdentifier: RouteElementIdentifier, animated: Boolean, completionHandler: RoutingCompletionHandler) {
        Log.d(TAG,"Fatal Errror --- start of arbitarty route")


    }

    override fun changeRouteSegment(from: RouteElementIdentifier, to: RouteElementIdentifier, animated: Boolean, completionHandler: RoutingCompletionHandler): Routable {

        if(from == itemNameActivityRoute && to == itemDetailActivityRoute) {
            return  RoutableHelper.createItemDetailActivityRoutable(context)
        } else {
            return this
        }


    }
}

class ItemNameActivityRoutable(val context: Context) : Routable {

    override fun pushRouteSegment(routeElementIdentifier: RouteElementIdentifier, animated: Boolean, completionHandler: RoutingCompletionHandler): Routable {
        TODO("not implemented")
    }

    override fun popRouteSegment(routeElementIdentifier: RouteElementIdentifier, animated: Boolean, completionHandler: RoutingCompletionHandler) {
    }

    override fun changeRouteSegment(from: RouteElementIdentifier, to: RouteElementIdentifier, animated: Boolean, completionHandler: RoutingCompletionHandler): Routable {
        TODO("not implemented")

    }
}

class ItemDetailActivityRoutable(val context: Context) : Routable {

    override fun pushRouteSegment(routeElementIdentifier: RouteElementIdentifier, animated: Boolean, completionHandler: RoutingCompletionHandler): Routable {
        if(routeElementIdentifier == itemNameActivityRoute){
            return RoutableHelper.createItemNameActivity(context)
        }
        return this
    }

    override fun popRouteSegment(routeElementIdentifier: RouteElementIdentifier, animated: Boolean, completionHandler: RoutingCompletionHandler) {
    }

    override fun changeRouteSegment(from: RouteElementIdentifier, to: RouteElementIdentifier, animated: Boolean, completionHandler: RoutingCompletionHandler): Routable {
        TODO("not implemented")
    }
}

