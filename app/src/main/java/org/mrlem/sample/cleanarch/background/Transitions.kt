package org.mrlem.sample.cleanarch.background

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import org.mrlem.sample.arch.BaseTransitions
import org.mrlem.sample.cleanarch.AllTogetherTransition
import org.mrlem.sample.cleanarch.R
import org.mrlem.sample.cleanarch.hud.SplitMode

class Transitions(root: ConstraintLayout) : BaseTransitions<SplitMode>(root) {

    override val transition = AllTogetherTransition()

    override fun update(state: SplitMode, constraints: ConstraintSet) = constraints.run {
        setVisibility(R.id.handle, if (state is SplitMode.Both) View.VISIBLE else View.INVISIBLE)
        setGuidelinePercent(R.id.split, state.ratio)
    }

}
