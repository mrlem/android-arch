package org.mrlem.sample.cleanarch.ui.main.hud

import androidx.lifecycle.distinctUntilChanged
import kotlinx.android.synthetic.main.fragment_hud.*
import kotlinx.android.synthetic.main.fragment_hud.view.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.mrlem.sample.arch.BaseFragment
import org.mrlem.sample.cleanarch.R

class HudFragment : BaseFragment() {

    override val layout = R.layout.fragment_hud
    private val viewModel by sharedViewModel<HudViewModel>()

    private val videoView by lazy { childFragmentManager.findFragmentById(R.id.video)!!.requireView() }
    private val mapView by lazy { childFragmentManager.findFragmentById(R.id.map)!!.requireView() }

    private val transitions by lazy { Transitions(requireView().hud, videoView, mapView) }

    override fun initViews() {
        transitions.applyState(viewModel.currentState)
    }

    override fun initEvents() {
        videoView.setOnClickListener { viewModel.restoreSplit() }
        mapView.setOnClickListener { viewModel.restoreSplit() }

        leftPanelButton.setOnClickListener { viewModel.toggleLeftPane() }
        rightPanelButton.setOnClickListener { viewModel.toggleRightPane() }
    }

    override fun initObservations() {
        viewModel.state
            .distinctUntilChanged()
            .observe(viewLifecycleOwner, transitions::applyState)
    }

}
