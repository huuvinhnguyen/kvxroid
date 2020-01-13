package com.ving.kvxroid.Redux

import org.rekotlin.Store

val mainStore = Store(
    reducer = ::appReducer,
    state = AppState(),
    middleware = listOf(connectionMiddleware, topicMiddleware, itemMiddleware, imagesMiddleware, ItemState.middleware()),
    automaticallySkipRepeats = true
)