package org.mrlem.sample.cleanarch.ui.hud

import org.mrlem.sample.arch.BaseViewModel

class HudViewModel : BaseViewModel<HudState>(HudState()) {

    ///////////////////////////////////////////////////////////////////////////
    // Panel related
    ///////////////////////////////////////////////////////////////////////////

    fun toggleLeftPane() {
        val mode = if (currentState.panelMode != PanelMode.Left) PanelMode.Left else PanelMode.None
        updateState { copy(panelMode = mode) }
    }

    fun toggleRightPane() {
        val mode = if (currentState.panelMode != PanelMode.Right) PanelMode.Right else PanelMode.None
        updateState { copy(panelMode = mode) }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Split related
    ///////////////////////////////////////////////////////////////////////////

    fun collapseSplitRight() {
        updateState { copy(splitMode = SplitMode.Right) }
    }

    fun collapseSplitLeft() {
        updateState { copy(splitMode = SplitMode.Left) }
    }

    fun adjustSplit(ratio: Float) {
        updateState { copy(splitMode = SplitMode.Both(ratio)) }
    }

    fun restoreSplit() = adjustSplit(0.75f)

}
