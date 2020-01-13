package com.ving.kvxroid.Common

import android.app.Application
import com.ving.kvxroid.Redux.AppState
import com.ving.kvxroid.Redux.RootRoutable
import com.ving.kvxroid.Redux.itemActivityRoute
import com.ving.kvxroid.Redux.mainStore
import org.rekotlinrouter.Router
import org.rekotlinrouter.SetRouteAction


var router: Router<AppState>? = null

open class BaseApplication : Application() {

    companion object {
        lateinit var INSTANCE: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        router = Router(store = mainStore,
            rootRoutable = RootRoutable(context = applicationContext),
            stateTransform = { subscription ->
                subscription.select { stateType ->
                    stateType.navigationState
                }
            })

    }

}