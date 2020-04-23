package org.mrlem.sample.cleanarch.hud

import android.transition.TransitionManager
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.distinctUntilChanged
import kotlinx.android.synthetic.main.fragment_hud.*
import kotlinx.android.synthetic.main.fragment_hud.view.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.mrlem.sample.arch.BaseFragment
import org.mrlem.sample.cleanarch.AllTogetherTransition
import org.mrlem.sample.cleanarch.R

class HudFragment : BaseFragment() {

    override val layout = R.layout.fragment_hud
    private val viewModel by sharedViewModel<HudViewModel>()

    private val videoFragment by lazy { childFragmentManager.findFragmentById(R.id.video) }
    private val mapFragment by lazy { childFragmentManager.findFragmentById(R.id.map) }

    private val constraints = ConstraintSet()

    override fun initViews() {
        constraints.clone(view!!.hud)
        applyTransitions(view!!, viewModel.state.value!!, false)
    }

    override fun initEvents() {
        videoFragment?.view?.setOnClickListener { viewModel.updateSplitMode(SplitMode.Both(0.75f)) }
        mapFragment?.view?.setOnClickListener { viewModel.updateSplitMode(SplitMode.Both(0.75f)) }
        leftPanelButton.setOnClickListener { viewModel.updatePanelMode(if (viewModel.currentState.panelMode != PanelMode.Left) PanelMode.Left else PanelMode.None) }
        rightPanelButton.setOnClickListener { viewModel.updatePanelMode(if (viewModel.currentState.panelMode != PanelMode.Right) PanelMode.Right else PanelMode.None) }
    }

    override fun initObservations() {
        viewModel.state
            .distinctUntilChanged()
            .observe(viewLifecycleOwner, Observer { state ->
                applyTransitions(view!!, state)
            })
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    private fun applyTransitions(view: View, state: HudState, animated: Boolean = true) {
        if (animated) {
            TransitionManager.beginDelayedTransition(view.hud,
                AllTogetherTransition()
            )
        }
        ConstraintSet().apply {
            clone(constraints)
            applyVideoConstraints(state.splitMode)
            applyMapConstraints(state.splitMode)
            applyLeftPanelConstraints(state.panelMode)
            applyRightPanelConstraints(state.panelMode)
            applyTo(view.hud)
        }
    }

    private fun ConstraintSet.applyVideoConstraints(splitMode: SplitMode) {
        val hasVideo = splitMode == SplitMode.Right
        videoFragment?.view?.apply {
            isVisible = hasVideo
        }
        if (!hasVideo) {
            clear(R.id.video, ConstraintSet.START)
            connect(R.id.video, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.START)
            setVerticalBias(R.id.video, 0.5f)
        }
    }

    private fun ConstraintSet.applyMapConstraints(splitMode: SplitMode) {
        val hasMap = splitMode == SplitMode.Left
        mapFragment?.view?.apply {
            isVisible = hasMap
        }
        if (!hasMap) {
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
