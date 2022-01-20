package com.example.easypark;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.easypark.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng auc = new LatLng(30.0186, 31.5015);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(auc,15));
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());

        // Add a marker in gate4 and move the camera
        LatLng gate4 = new LatLng(30.0166705, 31.5016696);
        mMap.addMarker(new MarkerOptions().position(gate4).title("Pepsi Parking").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(gate4));

        // Add a marker in gate5 and move the camera
        LatLng gate5 = new LatLng(30.0220150, 31.4971187);
        mMap.addMarker(new MarkerOptions().position(gate5).title("Omar Mohsen").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(gate5));

        // Add a marker in busgate and move the camera
        LatLng busgate = new LatLng(30.0176783, 31.4997065);
        mMap.addMarker(new MarkerOptions().position(busgate).title("Bus Gate").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(busgate));

        // Add a marker in watson and move the camera
        LatLng watson = new LatLng(30.0231389, 31.5011342);
        mMap.addMarker(new MarkerOptions().position(watson).title("Watson").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(watson));

        // Add a marker in watson and move the camera
        LatLng gardens = new LatLng(30.0218412, 31.5011342);
        mMap.addMarker(new MarkerOptions().position(gardens).title("Gardens").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(gardens));
    }
}