package org.mrlem.sample.cleanarch.ui.map

import android.animation.ValueAnimator
import android.content.Context
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.maps.SupportMapFragment
import org.mrlem.sample.arch.AllTogetherTransition
import org.mrlem.sample.arch.BaseFragment
import org.mrlem.sample.cleanarch.R

class MapFragment : BaseFragment() {

    override val layout = R.layout.fragment_map
    private lateinit var map: MapboxMap
    private var padding = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Mapbox.getInstance(context, "pk.eyJ1IjoibXJsZW0iLCJhIjoiY2s5YTJicDEwMGduNjNlbjJqdWY1OTBjeiJ9.MySWK2jRlt1YsZantxqyfw")
    }

    override fun initViews() {
        (childFragmentManager.findFragmentById(R.id.mapbox) as? SupportMapFragment)
            ?.let { mapboxFragment ->
                mapboxFragment.getMapAsync { map ->
                    onMapPrepare(map)
                    map.setStyle(Style.SATELLITE_STREETS) { onMapReady(map) }
                }
            }
    }

    fun setPadding(padding: Int, animated: Boolean) {
        if (::map.isInitialized) {
            if (animated) {
                val from = this.padding
                ValueAnimator.ofFloat(0f, 1f)
                    .apply {
                        addUpdateListener {
                            val update = CameraUpdateFactory.paddingTo(0.0, 0.0, (from + (padding - from) * it.animatedFraction).toDouble(), 0.0)
                            map.moveCamera(update)
                        }
                        interpolator = AllTogetherTransition.INTERPOLATOR
                        duration = AllTogetherTransition.DURATION
                    }
                    .start()
            } else {
                val update = CameraUpdateFactory.paddingTo(0.0, 0.0, padding.toDouble(), 0.0)
                map.moveCamera(update)
            }
        }
        this.padding = padding
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    private fun onMapPrepare(map: MapboxMap) {
        this.map = map

        // position camera
        map.cameraPosition = CameraPosition.Builder()
            .target(LatLng(48.1147, -1.6794))
            .zoom(12.0)
            .padding(0.0, 0.0, padding.toDouble(), 0.0)
            .build()
    }

    private fun onMapReady(map: MapboxMap) {
        map.addOnMapClickListener {
            view?.performClick()
            true
        }
        map.uiSettings.setAllGesturesEnabled(false)
        map.uiSettings.isLogoEnabled = false
    }

}
