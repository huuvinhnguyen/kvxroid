package com.ving.kvxroid.Redux

import org.rekotlin.Store

val mainStore = Store(
    reducer = ::counterReducer,
    state = null,
    middleware = listOf(connectionMiddleware, topicMiddleware, itemMiddleware, imagesMiddleware)
)