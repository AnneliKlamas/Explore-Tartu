package com.example.exploretartu.ui.map

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.exploretartu.MainActivity
import com.example.exploretartu.R
import com.example.exploretartu.ui.scanner.ScannerActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.gson.JsonObject
import com.google.maps.android.PolyUtil
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.activity_map.*


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private val SCANNER_REQUEST_CODE = 100
    private lateinit var mMap: GoogleMap
    private lateinit var helper: LocationHelper
    private lateinit var task: com.example.exploretartu.dao.Task


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        task = if(intent.getBooleanExtra("isTask1",true)){
            intent.getParcelableExtra("task1")!!
        }
        else{
            intent.getParcelableExtra("task2")!!
        }

        tv_visit.text = getString(R.string.visit, task.taskName)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        bt_scanner.setOnClickListener{
            startScannerActivity()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //For handeling perrmissions
            Dexter.withContext(this)
                .withPermissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        report.let {
                            if(report.areAllPermissionsGranted()){
                                Toast.makeText(applicationContext, "OK", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest?>?, token: PermissionToken?) {
                        token?.continuePermissionRequest()
                    }
                }).check()
        }
        mMap.isMyLocationEnabled = true

        helper = LocationHelper(applicationContext)

        val destLocation = LatLng(task.taskLocation[0], task.taskLocation[1])
        val myLocation= helper.getCurrentLocationUsingGPS()
        var curPos = LatLng(0.0, 0.0)
        myLocation?.apply {
            curPos = LatLng(this.latitude, this.longitude)
            destLocation.apply {
                findAndDrawPath(curPos, destLocation)
            }
        }
        mMap.setOnMapLoadedCallback {
            val builder = LatLngBounds.Builder()
            builder.include(curPos)
            builder.include(destLocation)
            val bounds: LatLngBounds = builder.build()

            val cu: CameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 100)
            mMap.animateCamera(cu, object : GoogleMap.CancelableCallback {
                override fun onCancel() {}
                override fun onFinish() {
                }
            })
        }
    }

    private fun findAndDrawPath(currentPos: LatLng, destPos: LatLng){
        mMap.clear()
        var key: String
        applicationContext.packageManager.getApplicationInfo(application.packageName, PackageManager.GET_META_DATA).apply { key =
            metaData.getString("com.google.android.geo.API_KEY").toString()
        }
        val url = "https://maps.googleapis.com/maps/api/directions/json?origin=${currentPos.latitude},${currentPos.longitude}&destination=${destPos.latitude},${destPos.longitude}&mode=walking&key=$key"

        val waypoints: MutableList<List<LatLng>> = ArrayList()

        val geocoder = Geocoder(this)
        val addresses = geocoder.getFromLocation(destPos.latitude, destPos.longitude, 1)
        val loc = addresses[0].getAddressLine(0)

        var distance: String
        var duration: String
        Ion.with(this)
            .load(url)
            .asJsonObject()
            .setCallback { e, result ->

                val response: JsonObject = result
                val routes = response.getAsJsonArray("routes")
                val legs = routes[0].asJsonObject.get("legs").asJsonArray
                distance = legs[0].asJsonObject.get("distance").asJsonObject.get("text").asString
                duration = legs[0].asJsonObject.get("duration").asJsonObject.get("text").asString
                val steps = legs[0].asJsonObject.get("steps").asJsonArray
                for (i in 0 until steps.size()) {
                    val points = steps.get(i).asJsonObject.get("polyline").asJsonObject.get("points").asString

                    waypoints.add(PolyUtil.decode(points))
                }
                for (i in 0 until waypoints.size) {
                    mMap.addPolyline(PolylineOptions().addAll(waypoints[i]).color(Color.RED))
                }

                mMap.addMarker(MarkerOptions().position(destPos).title(loc).snippet("Travel time: $duration (distance: $distance)"))
            }

    }

    private fun startScannerActivity(){
        val intent = Intent(this, ScannerActivity::class.java)
        startActivityForResult(intent, SCANNER_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SCANNER_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
                val scanResult: String? = data?.getStringExtra("scanResult")
                Toast.makeText(this, "$scanResult", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("snake", true)
                startActivity(intent)
                finish()
            }
        }
    }
}