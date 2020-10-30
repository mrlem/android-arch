package org.mrlem.sample.cleanarch.ui.background

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import org.mrlem.sample.arch.AllTogetherTransition
import org.mrlem.sample.arch.BaseTransitions
import org.mrlem.sample.cleanarch.R
import org.mrlem.sample.cleanarch.ui.hud.SplitMode

class Transitions(root: ConstraintLayout) : BaseTransitions<SplitMode>(root) {

    override val transition = AllTogetherTransition()

    override fun update(state: SplitMode, constraints: ConstraintSet) = constraints.run {
        setVisibility(R.id.handle, if (state is SplitMode.Both) View.VISIBLE else View.INVISIBLE)
        setGuidelinePercent(R.id.split, state.ratio)
    }

}
