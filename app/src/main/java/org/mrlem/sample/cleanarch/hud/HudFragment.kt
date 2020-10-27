package org.mrlem.sample.cleanarch.hud

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.distinctUntilChanged
import kotlinx.android.synthetic.main.fragment_hud.*
import kotlinx.android.synthetic.main.fragment_hud.view.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.mrlem.sample.arch.BaseFragment
import org.mrlem.sample.cleanarch.R

class HudFragment : BaseFragment() {

    override val layout = R.layout.fragment_hud
    private val viewModel by sharedViewModel<HudViewModel>()

    private val videoFragment by lazy { childFragmentManager.findFragmentById(R.id.video)!! }
    private val mapFragment by lazy { childFragmentManager.findFragmentById(R.id.map)!! }

    private val transitions by lazy { Transitions(requireView().hud) }

    override fun initViews() {
        transitions.applyState(viewModel.currentState)
    }

    override fun initEvents() {
        videoFragment.requireView().setOnClickListener { viewModel.updateSplitMode(SplitMode.Both(0.75f)) }
        mapFragment.requireView().setOnClickListener { viewModel.updateSplitMode(SplitMode.Both(0.75f)) }
        leftPanelButton.setOnClickListener { viewModel.updatePanelMode(if (viewModel.currentState.panelMode != PanelMode.Left) PanelMode.Left else PanelMode.None) }
        rightPanelButton.setOnClickListener { viewModel.updatePanelMode(if (viewModel.currentState.panelMode != PanelMode.Right) PanelMode.Right else PanelMode.None) }
    }

    override fun initObservations() {
        viewModel.state
            .distinctUntilChanged()
            .observe(viewLifecycleOwner, Observer { state ->
                videoFragment.requireView().isVisible = state.splitMode == SplitMode.Right
                mapFragment.requireView().isVisible = state.splitMode == SplitMode.Left
                transitions.applyState(state)
            })
    }

}
