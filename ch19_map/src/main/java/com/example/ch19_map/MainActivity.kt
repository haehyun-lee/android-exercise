package com.example.ch19_map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnSuccessListener

class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {
    lateinit var providerClient: FusedLocationProviderClient    // 위치 정보 얻기용
    lateinit var apiClient: GoogleApiClient                     // 위치 제공자 준비 등 콜백 제공
    var googleMap: GoogleMap? = null                            // 지도 객체

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        OnMapReadyCallback을 구현한 객체를 SupportMapFragment의 getMapAsync() 함수에 전달하면
        지도 객체를 이용할 수 있을 때 onMapReady() 함수가 자동호출되면서 매개변수로 GoogleMap 객체를 전달해준다.
         */
        (supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?)!!.getMapAsync(this)

        // FusedLocationProviderClient 초기화
        providerClient = LocationServices.getFusedLocationProviderClient(this)

        // GoogleApiClient 초기화
        apiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()

        // 퍼미션 허용 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) !== PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_NETWORK_STATE
                ),
                100
            )
        } else {
            // GoogleApiClient 객체에 위치 제공자 요청
            apiClient.connect()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 &&
            grantResults[0] === PackageManager.PERMISSION_GRANTED &&
            grantResults[1] === PackageManager.PERMISSION_GRANTED &&
            grantResults[2] === PackageManager.PERMISSION_GRANTED
        ) {
            apiClient.connect()
        }
    }

    // 위도, 경도 위치로 지도 이동
    private fun moveMap(latitude: Double, longitude: Double) {
        // onMapReady() 메서드에서 초기화된 googleMap 변수 사용

        val latLng = LatLng(latitude, longitude)        // 지도에서 한 지점
        val position = CameraPosition.Builder()
            .target(latLng)
            .zoom(16f)
            .build()
        // 지도 중심 이동
        googleMap?.moveCamera(CameraUpdateFactory.newCameraPosition(position))

        // 마커 옵션
        val markerOptions = MarkerOptions()
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        markerOptions.position(latLng)
        markerOptions.title("네이버웹툰 본사")
        markerOptions.snippet("Tel:01-120")
        
        // 마커 표시하기
        googleMap?.addMarker(markerOptions)
    }

    // 위치 제공자를 사용할 수 있는 상황일 때
    override fun onConnected(p0: Bundle?) {
        // 권한 체크
        if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED
        ) {
            // 위치 정보 얻기, 결과값은 onSuccess()로 받음
            providerClient.lastLocation.addOnSuccessListener (
                this@MainActivity,
                object : OnSuccessListener<Location> {
                    override fun onSuccess(location: Location?) {
                        // location이 null이 아닐 때만 이동
                        location?.let {
                            val latitude = it.latitude
                            val longitude = it.longitude
                            // 지도 중심 이동
                            moveMap(latitude, longitude)
                        }
                    }
                }
            )
        }
    }

    override fun onConnectionSuspended(p0: Int) {
        // 위치 제공자를 사용할 수 없을 때
    }


    override fun onConnectionFailed(p0: ConnectionResult) {
        // 사용할 수 있는 위치 제공자가 없을 때
    }

    // 지도 객체를 이용할 수 있는 상황이 될 때
    override fun onMapReady(p0: GoogleMap?) {
        // 매개변수로 받은 지도 객체를 변수로 참조
        googleMap = p0
    }
}