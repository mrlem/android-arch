package org.mrlem.android.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment

/**
 * Base fragment to be extended by feature fragments.
 *
 * Provides:
 * - easy layout declaration
 * - handy callbacks to categorize init code
 */
abstract class BaseFragment : Fragment() {

    protected abstract val layout: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layout, container, false)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initEvents()
        initObservations()
    }

    /**
     * Initialize the views.
     */
    protected open fun initViews() {}

    /**
     * Initialize view event listeners.
     */
    protected open fun initEvents() {}

    /**
     * Initialize view-model state observations.
     *
     * Note: when observing the state livedata here, please do use viewLifecycleOwner.
     */
    protected open fun initObservations() {}

}
