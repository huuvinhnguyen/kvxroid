package com.ving.kvxroid.Redux
import org.rekotlin.Action

data class CounterActionIncrease(val unit: Unit = Unit): Action
data class CounterActionDecrease(val unit: Unit = Unit): Action