package com.blockbasti.cragtrackapp.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.blockbasti.cragtrackapp.R
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import timber.log.Timber


class LocationSelectorActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mAutocompleteFragment: AutocompleteSupportFragment

    private lateinit var mPlacesClient: PlacesClient
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private var mLastKnownLocation: Location? = null
    private var mLocationPermissionGranted = false

    private lateinit var mLikelyPlaceNames: Array<String?>
    private lateinit var mLikelyPlaceAttributions: Array<List<String>?>
    private lateinit var mLikelyPlaceIds: Array<String?>
    private lateinit var mLikelyPlaceLatLngs: Array<LatLng?>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_selector)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Initialize the SDK
        Places.initialize(applicationContext, getString(R.string.google_maps_key))
        mPlacesClient = Places.createClient(this)

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        mAutocompleteFragment = supportFragmentManager.findFragmentById(R.id.map_autocomplete) as AutocompleteSupportFragment
        mAutocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))

        mAutocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Timber.i("Place: %s Id: %s LatLong: %s ", place.name, place.id, place.latLng)
                AlertDialog.Builder(this@LocationSelectorActivity).setTitle(R.string.select_location).setMessage(place.name).apply {
                    setPositiveButton(android.R.string.yes) { dialog, which ->
                        run {
                            val result = Intent()
                            result.putExtra("place_id", place.id)
                            result.putExtra("place_name", place.name)
                            result.putExtra("place_latlng", place.latLng)
                            setResult(Activity.RESULT_OK, result)
                            finish()
                        }
                    }
                }.apply {
                    setNegativeButton(android.R.string.no) { dialog, which ->
                        run {

                        }
                    }
                }.create().show()

            }

            override fun onError(status: Status) {
                Timber.e(status.statusMessage)
            }

        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        mLocationPermissionGranted = false
        if (requestCode == 0 && grantResults.count() > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) mLocationPermissionGranted = true
        updateLocationUI()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getLocationPermission()
        updateLocationUI()
        getDeviceLocation()
    }

    private fun getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                val locationResult = mFusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener { task ->
                    run {
                        if (task.isSuccessful) {
                            if (task.result != null) {
                                mLastKnownLocation = task.result
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(mLastKnownLocation?.latitude ?: .0, mLastKnownLocation?.longitude ?: .0), 15f))
                            } else {
                                Timber.e("Location was null")
                                Timber.e(task.exception)
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 0.0), 15f))
                                mMap.uiSettings.isMyLocationButtonEnabled = false
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private fun updateLocationUI() {
        try {
            if (mLocationPermissionGranted) {
                mMap.isMyLocationEnabled = true
                mMap.uiSettings.isMyLocationButtonEnabled = true
            } else {
                mMap.isMyLocationEnabled = false
                mMap.uiSettings.isMyLocationButtonEnabled = false
                mLastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) mLocationPermissionGranted = true
        else ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 0)
    }
}
