package org.mrlem.sample.cleanarch.background

import android.transition.TransitionManager
import android.view.*
import android.view.View.*
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.Observer
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import kotlinx.android.synthetic.main.fragment_background.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.mrlem.sample.arch.BaseFragment
import org.mrlem.sample.cleanarch.AllTogetherTransition
import org.mrlem.sample.cleanarch.MapFragment
import org.mrlem.sample.cleanarch.R
import org.mrlem.sample.cleanarch.hud.HudViewModel
import org.mrlem.sample.cleanarch.hud.SplitMode

class BackgroundFragment : BaseFragment() {

    override val layout = R.layout.fragment_background
    private val viewModel by sharedViewModel<HudViewModel>()
    private val mapFragment by lazy { childFragmentManager.findFragmentById(R.id.map) as MapFragment }
    private val constraints = ConstraintSet()

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

    override fun initEvents() {
        constraints.clone(background)

        handle.setOnTouchListener(touchListener)
        viewModel.state
            .map { it.splitMode }
            .distinctUntilChanged()
            .observe(viewLifecycleOwner, Observer { splitMode ->
                val newConstraints = ConstraintSet()
                newConstraints.clone(constraints)
                newConstraints.setVisibility(
                    R.id.handle,
                    if (splitMode is SplitMode.Both) VISIBLE else INVISIBLE
                )
                val ratio = when (splitMode) {
                    SplitMode.Left -> 1f
                    SplitMode.Right -> 0f
                    is SplitMode.Both -> splitMode.ratio // xml constant shared with xml
                }
                newConstraints.setGuidelinePercent(R.id.split, ratio)
                if (!dragging) {
                    TransitionManager.beginDelayedTransition(background, AllTogetherTransition())
                }
                newConstraints.applyTo(background)

                when (splitMode) {
                    is SplitMode.Right -> mapFragment.setPadding(0, true)
                    is SplitMode.Left -> mapFragment.setPadding(view!!.width, true)
                    is SplitMode.Both -> mapFragment.setPadding(
                        (view!!.width * ratio).toInt(),
                        !dragging
                    )
                }
            })

        // when the view get rendered, view will be measured, so we know which padding to apply
        view!!.post {
            mapFragment.setPadding(split.x.toInt(), false)
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
