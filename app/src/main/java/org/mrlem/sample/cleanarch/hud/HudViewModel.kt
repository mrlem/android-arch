package org.mrlem.sample.cleanarch.hud

import androidx.annotation.FloatRange
import org.mrlem.sample.arch.BaseViewModel
import org.mrlem.sample.arch.State

class HudViewModel : BaseViewModel<HudState>(HudState()) {

    fun updateSplitMode(mode: SplitMode) {
        updateState { copy(splitMode = mode) }
    }

    fun updatePanelMode(mode: PanelMode) {
        updateState { copy(panelMode = mode) }
    }

}

data class HudState(
    var splitMode: SplitMode = SplitMode.Both(0.75f),
    var panelMode: PanelMode = PanelMode.None
) : State

sealed class SplitMode {
    object Left : SplitMode()
    object Right : SplitMode()
    data class Both(
        @FloatRange(from = 0.0, to = 1.0) val ratio: Float
    ) : SplitMode()
}

sealed class PanelMode {
    object Left : PanelMode()
    object Right : PanelMode()
    object None : PanelMode()
}