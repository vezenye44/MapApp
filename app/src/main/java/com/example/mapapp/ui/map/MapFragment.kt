package com.example.mapapp.ui.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.mapapp.R
import com.example.mapapp.data.location_provider.LocationProvider
import com.example.mapapp.data.location_provider.LocationProviderImpl
import com.example.mapapp.databinding.FragmentMapsBinding
import com.example.mapapp.ui.markers.MarkersListFragment
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.runtime.ui_view.ViewProvider
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapFragment : Fragment() {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private lateinit var locationProvider: LocationProvider
    private val mapViewModel: MapViewModel by viewModel<MapViewModel>()

    private val listView = mutableListOf<View>()

    private var map: Map? = null

    private val inputListener = object : InputListener {
        override fun onMapTap(p0: Map, p1: Point) {}

        override fun onMapLongTap(map: Map, point: Point) {
            mapViewModel.addMarker(point.latitude, point.longitude)
        }

    }

    private fun addMarker(point: Point) {
        val drawable = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_marker)
        val view = View(requireContext()).apply {
            background = drawable
        }
        listView.add(view)

        map?.mapObjects?.addPlacemark(point, ViewProvider(view))
    }


    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
        ) { _ -> }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        locationProvider = LocationProviderImpl(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMapsBinding.inflate(layoutInflater, container, false)

        map = binding.mapview.mapWindow.map

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapViewModel.markerLiveData().observe(viewLifecycleOwner) {
            it.forEach { marker ->
                val point = Point(marker.latitude, marker.longitude)
                addMarker(point)
            }
        }

        initMap()

        binding.myLocationFab.setOnClickListener {
            requireContext().let { context ->
                if (checkGPSPermissions(context)) {
                    updateMyLocation()
                } else {
                    requestGPSPermissions()
                }
            }
        }
        binding.markersListFab.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .hide(this)
                .add(
                    R.id.container,
                    MarkersListFragment.newInstance(),
                    ""
                )
                .addToBackStack("")
                .commit()
        }
    }

    private fun updateMyLocation() {
        val location = locationProvider.getLocation()
        location?.let {
            map?.move(
                CameraPosition(
                    Point(location.latitude, location.longitude), 14.0f, 0.0f, 0.0f
                ),
                Animation(Animation.Type.SMOOTH, 1f),
                null

            )
        }
    }

    private fun initMap() {
        map?.let { map ->
            map.move(
                CameraPosition(
                    Point(59.945933, 30.320045), 14.0f, 0.0f, 0.0f
                ),
                Animation(Animation.Type.SMOOTH, 1f),
                null
            )

            map.addInputListener(inputListener)
        }
    }

    private fun requestGPSPermissions() {
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun checkGPSPermissions(context: Context) = ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    override fun onStop() {
        binding.mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapview.onStart()
    }

    companion object {

        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }
}