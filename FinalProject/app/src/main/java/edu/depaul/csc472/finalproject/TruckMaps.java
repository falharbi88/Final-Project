package edu.depaul.csc472.finalproject;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import edu.depaul.csc472.finalproject.Model.Truck;

public class TruckMaps extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private static final int ERROR_DIALOG_REQUEST = 900;
    private GoogleApiClient mLocationClient;
    public static final int MAP_CODE = 100;
    public HashMap<String, Truck> myTruck = new HashMap<>();

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
        servicesOk();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        loadData();
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
        int i =0;
        for (i = 0; i < HomeActivity.myTrucks.size(); i++) {
            String location = HomeActivity.myTrucks.get(i).getTruckLocation();
            String myString[] = location.split(",");
            Double lat = Double.valueOf(myString[0]);
            Double lng = Double.valueOf(myString[1]);
            LatLng latLang = new LatLng(lat, lng);
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLang, 15);
            myTruck.put(HomeActivity.myTrucks.get(i).getTruckName(),HomeActivity.myTrucks.get(i));
            mMap.addMarker(new MarkerOptions()
                    .position(latLang)
                    .title(HomeActivity.myTrucks.get(i).getTruckName())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.foodtruck))
                    .snippet(HomeActivity.myTrucks.get(i).getTruckType()));
            mMap.moveCamera(update);

            final int finalI = i;
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    View v = getLayoutInflater().inflate(R.layout.info_window, null);
                    TextView truckName = (TextView) v.findViewById(R.id.TruckName);
                    TextView truckType = (TextView) v.findViewById(R.id.TruckType);
                    ImageView truckImage = v.findViewById(R.id.imageView1);
                    truckName.setText(marker.getTitle());
                    truckType.setText(marker.getSnippet());
                    if (myTruck.get(marker.getTitle()) != null) {
                        Picasso.with(TruckMaps.this).load(myTruck.get(marker.getTitle()).getTruckImage()).into(truckImage);
                    }
                    return v;

                }
            });

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    if(marker.getTitle().equals("current location")) {
                        return;
                    }else{
                            String truckName = marker.getTitle();
                            Intent menuActivity = new Intent(TruckMaps.this, MenuActivity.class);
                            menuActivity.putExtra("TruckName", truckName);
                            //startActivity(menuActivity);
                            startActivityForResult(menuActivity, MAP_CODE);
                    }

                }


            });


        }

    }


    protected void onActivityResult(int requestedCode, int resultCode, Intent data){
        if(requestedCode==MAP_CODE){
            if(resultCode==RESULT_OK){

            }
        }


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this, "Ready To Map", Toast.LENGTH_LONG).show();

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
        currentLocation.setLongitude(-87.625564);
        currentLocation.setLatitude(41.878502);
        if (currentLocation == null) {
            Toast.makeText(this, "Current Location is Null", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Current Location is detected", Toast.LENGTH_LONG).show();
            LatLng latLang = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLang, 15);
            mMap.addMarker(new MarkerOptions().position(latLang).title("current location").snippet("You are here"));
            mMap.moveCamera(update);

        }
        return;



    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
