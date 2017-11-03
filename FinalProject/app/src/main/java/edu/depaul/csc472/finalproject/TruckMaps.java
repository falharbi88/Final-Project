package edu.depaul.csc472.finalproject;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TruckMaps extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private static final int ERROR_DIALOG_REQUEST = 900;
    private GoogleApiClient mLocationClient;
    private LocationListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mLocationClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mLocationClient.connect();
        if (servicesOk()) {

        }
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

        // Add a marker in Sydney and move the camera
        //41.878502, -87.625564
        //LatLng DePaulCDM = new LatLng(41.878502,-87.625564);
        //mMap.addMarker(new MarkerOptions().position(DePaulCDM).title("Current Location"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(DePaulCDM));
        //
        loadData();
        //goToLocation(41.878502,-87.625564,15);
    }


    public boolean servicesOk() {

        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't Connect to Map Service", Toast.LENGTH_LONG).show();
        }

        return false;
    }


    private void goToLocation(double lat, double lng, float zoom) {
        LatLng latLang = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLang, zoom);
        mMap.addMarker(new MarkerOptions().position(latLang).title("Current Location"));
        mMap.moveCamera(update);

    }

    private void loadData() {

        for (int i = 0; i < HomeActivity.myTrucks.size(); i++) {
            String location = HomeActivity.myTrucks.get(i).getTruckLocation();
            String myString[] = location.split(",");
            Double lat = Double.valueOf(myString[0]);
            Double lng = Double.valueOf(myString[1]);
            LatLng latLang = new LatLng(lat, lng);
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLang, 15);
            mMap.addMarker(new MarkerOptions().position(latLang).title(HomeActivity.myTrucks.get(i).getTruckName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.foodtruck)));
            mMap.moveCamera(update);


        }

    }


    private void displayCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this, "Permission are not allowed", Toast.LENGTH_LONG).show();
            return;
        }
        Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);
        if (currentLocation == null) {
            Toast.makeText(this, "Current Location is Null", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Current Location is detected", Toast.LENGTH_LONG).show();
            LatLng latLang = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLang, 15);
            mMap.addMarker(new MarkerOptions().position(latLang).title("current location"));
            mMap.moveCamera(update);

        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this, "Ready To Map", Toast.LENGTH_LONG).show();
        displayCurrentLocation();
        return;

//        mListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                goToLocation(location.getLatitude(), location.getLongitude(), 15);
//                Toast.makeText(TruckMaps.this, location.toString(), Toast.LENGTH_LONG).show();
//
//            }
//        };
//        LocationRequest request = LocationRequest.create();
//        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        request.setInterval(5000);
//        request.setFastestInterval(1000);
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, request, mListener);


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

//    @Override
//    public void onPause(){
//        super.onPause();
//        LocationServices.FusedLocationApi.removeLocationUpdates(mLocationClient,mListener);
//    }

}
