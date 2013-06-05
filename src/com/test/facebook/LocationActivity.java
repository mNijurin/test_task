package com.test.facebook;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.test.facebook.data.TestTaskConstants;


/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 6/1/13
 * Time: 11:15 PM
 */
public class LocationActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    private GoogleMap gMap;
    private LocationClient mLocationClient;
    private Location location;
    private Marker marker;

    private static final LocationRequest REQUEST = LocationRequest.create()
        .setInterval(5000)         // 5 seconds
        .setFastestInterval(16)    // 16ms = 60fps
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);

        setupMapIfNeeded();
    }

    private void setupMapIfNeeded(){
        if(gMap == null){
            gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

            if(gMap != null){
                gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


                gMap.setMyLocationEnabled(true);
                gMap.getUiSettings().setMyLocationButtonEnabled(true);

                gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        if(marker == null){
                            marker = gMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .title(getString(R.string.tip_for_long_press)));
                        } else
                            marker.setPosition(latLng);
                        marker.setDraggable(true);
                    }
                });
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupMapIfNeeded();
        setUpLocationClientIfNeeded();
        mLocationClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mLocationClient != null) {
            mLocationClient.disconnect();
        }
    }

    private void setUpLocationClientIfNeeded() {
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(
                    getApplicationContext(),
                    this,  // ConnectionCallbacks
                    this); // OnConnectionFailedListener
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationClient.requestLocationUpdates(
                REQUEST,
                this);  // LocationListener
    }

    @Override
    public void onDisconnected() {
        // Do nothing
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Do nothing
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    public void onCompleteListener(View v){
        Intent returnIntent = new Intent();
        returnIntent.putExtra(TestTaskConstants.SELF_LOCATION,
                location != null ? "(" + location.getLatitude() + "," + location.getLongitude() + ")" : getString(R.string.self_location_not_available));
        returnIntent.putExtra(TestTaskConstants.SELECTED_LOCATION,
                marker != null ? marker.getPosition().toString() : getString(R.string.place_not_selected));
        setResult(RESULT_OK,returnIntent);
        finish();
    }
}
