package org.mrlem.android.arch

import android.transition.ChangeBounds
import android.transition.Fade
import android.transition.TransitionSet
import android.view.animation.AccelerateDecelerateInterpolator

class AllTogetherTransition : TransitionSet() {

    init {
        duration = DURATION
        interpolator = INTERPOLATOR
        ordering = ORDERING_TOGETHER
        addTransition(Fade(Fade.OUT))
        addTransition(ChangeBounds())
        addTransition(Fade(Fade.IN))
    }

    companion object {
        const val DURATION = 250L
        val INTERPOLATOR = AccelerateDecelerateInterpolator()
    }

}
