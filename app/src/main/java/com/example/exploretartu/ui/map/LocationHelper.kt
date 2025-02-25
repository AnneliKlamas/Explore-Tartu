package com.example.exploretartu.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat

class LocationHelper(private val mContext: Context) : LocationListener {
    companion object {
        const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10 // meters
        // The minimum time between updates in milliseconds:
        const val MIN_TIME_BW_UPDATES = (1000 * 60 * 1).toLong() // 1 minute
    }
    // Flags
    private var isGPSEnabled = false
    private var isNetworkEnabled = false

    private var locationManager: LocationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    init {
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocationUsingGPS(): Location? {
        var location: Location? = null
        if (checkPermissions()) {
            Log.v("TAG ","No permission Granted")
        } else {
            if (!isGPSEnabled) {
                Log.v("TAG ","GPS not enabled!")
            } else {
                // If GPS enabled, get latitude/longitude using GPS Services
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                )
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }
        }
        return location
    }

    private fun checkPermissions(): Boolean {
        return (ActivityCompat.checkSelfPermission( mContext, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission( mContext, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED)
    }



    override fun onLocationChanged(location: Location) {
        getCurrentLocationUsingGPS()
    }
    override fun onProviderDisabled(provider: String) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

}