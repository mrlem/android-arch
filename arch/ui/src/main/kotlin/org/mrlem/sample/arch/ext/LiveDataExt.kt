package org.mrlem.sample.arch.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import org.mrlem.sample.arch.EventWrapper
import org.mrlem.sample.arch.EventObserver

fun <T> LiveData<out EventWrapper<T>>.onEvent(lifecycleOwner: LifecycleOwner, onEvent: (T) -> Unit) =
    observe(lifecycleOwner, EventObserver(onEvent))
