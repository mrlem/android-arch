package org.mrlem.sample.cleanarch

import android.transition.ChangeBounds
import android.transition.Fade
import android.transition.TransitionSet
import android.view.animation.AccelerateDecelerateInterpolator

class AllTogetherTransition : TransitionSet() {

    init {
        this.
        startDelay = 0
        duration = DURATION
        interpolator = INTERPOLATOR
        ordering = ORDERING_TOGETHER
        addTransition(Fade(Fade.OUT))
        addTransition(ChangeBounds())
        addTransition(Fade(Fade.IN))
    }

    companion object {
        // in xml
        const val DURATION = 250L
        val INTERPOLATOR = AccelerateDecelerateInterpolator()
    }

}
