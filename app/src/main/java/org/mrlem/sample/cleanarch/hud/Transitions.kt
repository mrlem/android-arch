package org.mrlem.sample.cleanarch.hud

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import org.mrlem.sample.arch.BaseTransitions
import org.mrlem.sample.cleanarch.AllTogetherTransition
import org.mrlem.sample.cleanarch.R

class Transitions(root: ConstraintLayout) : BaseTransitions<HudState>(root) {

    override val transition = AllTogetherTransition()

    override fun update(state: HudState, constraints: ConstraintSet) = constraints.run {
        applyVideoConstraints(state.splitMode)
        applyMapConstraints(state.splitMode)
        applyLeftPanelConstraints(state.panelMode)
        applyRightPanelConstraints(state.panelMode)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    private fun ConstraintSet.applyVideoConstraints(splitMode: SplitMode) {
        if (!splitMode.hasMiniVideo) {
            clear(R.id.video, ConstraintSet.START)
            connect(R.id.video, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.START)
            setVerticalBias(R.id.video, 0.5f)
        }
    }

    private fun ConstraintSet.applyMapConstraints(splitMode: SplitMode) {
        if (!splitMode.hasMiniMap) {
            clear(R.id.map, ConstraintSet.END)
            connect(R.id.map, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.END)
            setVerticalBias(R.id.map, 0.5f)
        }
    }

    private fun ConstraintSet.applyLeftPanelConstraints(panelMode: PanelMode) {
        val hasPanel = panelMode == PanelMode.Left
        if (!hasPanel) {
            clear(R.id.leftPanel, ConstraintSet.START)
            connect(R.id.leftPanel, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.START)
        }
    }

    private fun ConstraintSet.applyRightPanelConstraints(panelMode: PanelMode) {
        val hasPanel = panelMode == PanelMode.Right
        if (!hasPanel) {
            clear(R.id.rightPanel, ConstraintSet.END)
            connect(R.id.rightPanel, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.END)
        }
    }

}
