package org.mrlem.android.arch

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * Base activity to be extended by feature activities.
 *
 * Provides:
 * - easy layout declaration
 * - handy callbacks to categorize init code
 */
abstract class BaseActivity<ActivityViewBinding : ViewBinding> : AppCompatActivity() {

    abstract val binding: ActivityViewBinding

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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
     */
    protected open fun initObservations() {}

}
