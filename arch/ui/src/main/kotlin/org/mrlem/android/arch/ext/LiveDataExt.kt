package org.mrlem.android.arch.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import org.mrlem.android.arch.EventWrapper
import org.mrlem.android.arch.EventObserver

fun <T> LiveData<out EventWrapper<T>>.onEvent(lifecycleOwner: LifecycleOwner, onEvent: (T) -> Unit) =
    observe(lifecycleOwner, EventObserver(onEvent))
