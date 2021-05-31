package org.mrlem.android.arch

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * Base view-model to be extended by feature view-models.
 *
 * Provides:
 * - immutable state [LiveData] with update method
 * - handy callbacks to know when the view-model is being used or not
 * - rx composite disposables
 */
abstract class BaseViewModel<State : BaseState, Event>(initialState: State) : ViewModel() {

    private val _state = object : MutableLiveData<State>(initialState) {
        override fun onActive() = this@BaseViewModel.onStart()

        override fun onInactive() {
            this@BaseViewModel.onStop()
            disposeOnStop.clear()
        }
    }

    /**
     * [LiveData] of state changes.
     */
    val state: LiveData<State> = _state

    private val _events = MutableLiveData<EventWrapper<Event>>()

    /**
     * LiveData of events.
     */
    val events: LiveData<EventWrapper<Event>> = _events

    /**
     * Current state.
     */
    val currentState: State get() = state.value!!

    protected val disposeOnStop = CompositeDisposable()
    protected val disposeOnDestroy = CompositeDisposable()

    @CallSuper
    override fun onCleared() {
        disposeOnDestroy.clear()
    }

    /**
     * Update the current state. The new state must be a new state instance.
     *
     * ```
     * updateState { copy(field1 = value2) }
     * ```
     */
    protected fun updateState(transition: (State.() -> State)) {
        _state.value = transition(currentState)
    }

    /**
     * Send an event.
     */
    protected  fun notifyEvent(event: Event) {
        _events.value = EventWrapper(event)
    }

    /**
     * Called when the view-model is being used.
     */
    open fun onStart() {}

    /**
     * Called when the view-model is not being used anymore.
     */
    open fun onStop() {}

}
