package org.mrlem.sample.cleanarch.background

import android.view.*
import android.view.View.*
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import kotlinx.android.synthetic.main.fragment_background.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.mrlem.sample.arch.BaseFragment
import org.mrlem.sample.arch.ext.android.bind
import org.mrlem.sample.cleanarch.MapFragment
import org.mrlem.sample.cleanarch.R
import org.mrlem.sample.cleanarch.hud.HudViewModel
import org.mrlem.sample.cleanarch.hud.SplitMode

class BackgroundFragment : BaseFragment() {

    override val layout = R.layout.fragment_background
    private val viewModel by sharedViewModel<HudViewModel>()
    private val mapFragment by lazy { childFragmentManager.findFragmentById(R.id.map) as MapFragment }

    private val transitions by lazy { Transitions(container) }

    // split event handling
    private var dragging = false
    private var dx = 0f
    private val touchListener = OnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                dx = split.x - event.rawX
                dragging = true
            }
            MotionEvent.ACTION_UP -> dragging = false
            MotionEvent.ACTION_MOVE -> {
                if (dragging) {
                    view?.width?.let { width ->
                        val x = event.rawX + dx
                        val ratio = x / width.toFloat()
                        updateSplit(ratio)
                    }
                }
            }
        }
        true
    }

    override fun initViews() {
        transitions.applyState(viewModel.currentState.splitMode)

        // when the view get rendered, view will be measured, so we know which padding to apply
        requireView().post {
            mapFragment.setPadding(split.x.toInt(), false)
        }
    }

    override fun initEvents() {
        handle.setOnTouchListener(touchListener)
    }

    override fun initObservations() {
        viewModel.state
            .map { it.splitMode }
            .distinctUntilChanged()
            .bind(viewLifecycleOwner) { splitMode ->
                transitions.applyState(splitMode, !dragging)
                mapFragment.setPadding((splitMode.ratio * requireView().width).toInt(), !dragging)
            }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    //////////////////////////////////////////////////////////////////////////

    private fun updateSplit(ratio: Float) {
        when {
            ratio < magnetismDistance -> {
                dragging = false
                viewModel.updateSplitMode(SplitMode.Right)
            }
            ratio > 1 - magnetismDistance -> {
                dragging = false
                viewModel.updateSplitMode(SplitMode.Left)
            }
            else -> {
                viewModel.updateSplitMode(SplitMode.Both(ratio))
            }
        }
    }

    companion object {
        private const val magnetismDistance = 0.15f // xml constant
    }

}
