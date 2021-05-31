package org.mrlem.android.arch

import androidx.lifecycle.Observer

/**
 * An [Observer] for [EventWrapper]s, simplifying the pattern of checking if the [EventWrapper]'s content has
 * already been handled.
 *
 * [onEventUnhandledContent] is *only* called if the [EventWrapper]'s contents has not been handled.
 *
 * see [Google's todoapp](https://github.com/android/architecture-samples/blob/main/app/src/main/java/com/example/android/architecture/blueprints/todoapp/Event.kt)
 */
class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<EventWrapper<T>> {
    override fun onChanged(eventWrapper: EventWrapper<T>?) {
        eventWrapper?.getContentIfNotHandled()?.let {
            onEventUnhandledContent(it)
        }
    }
}