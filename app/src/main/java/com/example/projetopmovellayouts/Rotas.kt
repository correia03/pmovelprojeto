package com.example.projetopmovellayouts

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.projetopmovellayouts.api.EndPoints
import com.example.projetopmovellayouts.api.ParagemResponse
import com.example.projetopmovellayouts.api.ParagensRequest
import com.example.projetopmovellayouts.api.ServiceBuilder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Rotas : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var lastLocation: android.location.Location
    private var selectedRouteId: Int = 0
    private var userMarker: Marker? = null

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotas)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        createLocationRequest()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation!!
                var loc = LatLng(lastLocation.latitude, lastLocation.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15.0f))
                if (userMarker == null) {
                    userMarker = mMap.addMarker(MarkerOptions().position(loc).title("A sua localização"))
                } else {
                    userMarker?.position = loc
                }
                Log.d("**** TAG", "new location received - " + loc.latitude + " -" + loc.longitude)

                if (selectedRouteId != 0) {
                    findAndMarkClosestPoint(selectedRouteId, loc)
                }

            }

        }
        val spinner = findViewById<Spinner>(R.id.spinnerOptions)
        val options = resources.getStringArray(R.array.options)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                // Handle the selected item
                when (position) {
                    1 -> {
                        showRota("Rota Melgaço-ESTG", 1)
                        selectedRouteId = 1
                    }

                    2 -> {
                        showRota("Rota ESTG-Ponte de Lima", 2)
                        selectedRouteId = 2
                    }
                    // Add more cases for other options if needed
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Do nothing here if needed
            }
        }
    }
    private fun showRota(rotaName: String, rotaId: Int) {
        // Handle the logic for displaying the selected route
        Toast.makeText(this, "Selected Route: $rotaName", Toast.LENGTH_SHORT).show()

        // (PEREIRA) TODO: Call your web service with the rotaId
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getParagens(ParagensRequest(rotaId))

        call.enqueue(object : Callback<List<ParagemResponse>> {
            override fun onResponse(call: Call<List<ParagemResponse>>, response: Response<List<ParagemResponse>>) {
                if (response.isSuccessful) {
                    val paragens = response.body()!!

                    val points = ArrayList<LatLng>()

                    for (paragem in paragens) {
                        val location = LatLng(paragem.lat.toDouble(), paragem.long.toDouble())
                        points.add(location)
                        mMap.addMarker(MarkerOptions().position(location).title(paragem.nome))
                    }

                    if (points.size >= 2) {
                        // Draw polyline if there are at least two points
                        val polylineOptions = PolylineOptions()
                            .addAll(points)
                            .color(Color.BLUE)
                            .width(5f)

                        // Add Polyline to the map
                        mMap.addPolyline(polylineOptions)

                        // Move camera to the first point
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(points.first(), 15.0f))
                    }

                }
            }
            override fun onFailure(call: Call<List<ParagemResponse>>, t: Throwable) {
                Toast.makeText(this@Rotas, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        Log.d("**** TAG", "onPause - removeLocationUpdates")
    }
    public override fun onResume() {
        super.onResume()
        startLocationUpdates()
        Log.d("**** TAG", "onResume - startLocationUpdates")
    }

    fun cartao_digital(view: View) {
        val intent = Intent(this, cartaoDigital::class.java).apply {

        }
        startActivity(intent)
    }
    fun buyticket(view: View) {
        val intent = Intent(this, comprarBilhete::class.java)

        // Start the new activity
        startActivity(intent)
    }
    fun swaphistory(view: View) {
        val intent = Intent(this, History::class.java)

        // Start the new activity
        startActivity(intent)

    }
    fun personalinfo(view: View) {
        val intent = Intent(this, Personalinfo::class.java)

        // Start the new activity
        startActivity(intent)
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        // interval specifies the rate at which your app will like to receive updates.
        locationRequest.interval = 10000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    override fun onMapReady(googleMap: GoogleMap) {

        setUpMap()

        mMap = googleMap

    }

    fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
    }


    private fun findAndMarkClosestPoint(rotaId: Int, userLocation: LatLng) {
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getParagens(ParagensRequest(rotaId))

        call.enqueue(object : Callback<List<ParagemResponse>> {
            override fun onResponse(call: Call<List<ParagemResponse>>, response: Response<List<ParagemResponse>>) {
                if (response.isSuccessful) {
                    val paragens = response.body()!!

                    val points = ArrayList<LatLng>()

                    for (paragem in paragens) {
                        val location = LatLng(paragem.lat.toDouble(), paragem.long.toDouble())
                        points.add(location)
                        mMap.addMarker(MarkerOptions().position(location).title(paragem.nome))
                    }

                    if (points.isNotEmpty()) {
                        // Find the closest point to the user's location
                        val closestPoint = findClosestPoint(points, userLocation)

                        closestPoint?.let {
                            // Calculate distance to the closest point
                            val distance = calculateDistance(userLocation, it.position)

                            // Display the distance in a toast message
                            Toast.makeText(this@Rotas, "Closest Point: ${it.title}, Distance: ${distance} meters", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<ParagemResponse>>, t: Throwable) {
                Toast.makeText(this@Rotas, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun findClosestPoint(points: List<LatLng>, userLocation: LatLng): Marker? {
        var closestPoint: Marker? = null
        var minDistance = Double.MAX_VALUE

        for (point in points) {
            val distance = calculateDistance(userLocation, point)
            if (distance < minDistance) {
                minDistance = distance
                closestPoint = mMap.addMarker(MarkerOptions().position(point).title("Closest Point"))
            }
        }

        return closestPoint
    }

    private fun calculateDistance(location1: LatLng, location2: LatLng): Double {
        val result = FloatArray(1)
        Location.distanceBetween(location1.latitude, location1.longitude, location2.latitude, location2.longitude, result)
        return result[0].toDouble()
    }

}