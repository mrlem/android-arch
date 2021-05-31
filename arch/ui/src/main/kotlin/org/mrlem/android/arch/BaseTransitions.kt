package org.mrlem.android.arch

import android.transition.Transition
import android.transition.TransitionManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

abstract class BaseTransitions<S>(private val root: ConstraintLayout, private val onEnd: (() -> Unit)? = null) {

    private val constraints = ConstraintSet().apply { clone(root) }

    private val transitionListener = object : Transition.TransitionListener {
        override fun onTransitionStart(transition: Transition) {}
        override fun onTransitionResume(transition: Transition) {}
        override fun onTransitionPause(transition: Transition) {}
        override fun onTransitionEnd(transition: Transition) { onEnd?.invoke() }
        override fun onTransitionCancel(transition: Transition) {}
    }

    open val transition: Transition? = null

    fun applyState(state: S, animated: Boolean = true) {
        ConstraintSet().apply {
            clone(constraints)

            // update constraints
            update(state, this)

            // apply them
            if (animated) {
                transition?.removeListener(transitionListener)
                if (onEnd != null) {
                    transition?.addListener(transitionListener)
                }
                TransitionManager.beginDelayedTransition(root, transition)
            }
            applyTo(root)
        }
    }

    abstract fun update(state: S, constraints: ConstraintSet)

}
