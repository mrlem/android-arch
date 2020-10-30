package org.mrlem.sample.cleanarch.ui.main.hud

import androidx.annotation.FloatRange
import org.mrlem.sample.arch.State

data class HudState(
    var splitMode: SplitMode = SplitMode.Both(0.75f),
    var panelMode: PanelMode = PanelMode.None
) : State

sealed class SplitMode {

    abstract val ratio: Float
    val hasMiniVideo get() = this == Right
    val hasMiniMap get() = this == Left

    object Left : SplitMode() {
        override val ratio = 1f
    }

    object Right : SplitMode() {
        override val ratio = 0f
    }

    data class Both(
        @FloatRange(from = 0.0, to = 1.0) override val ratio: Float
    ) : SplitMode()

}

sealed class PanelMode {

    object Left : PanelMode()
    object Right : PanelMode()
    object None : PanelMode()

}
