package com.carloudy.osmdroidpractice;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private final int LOCATION_PERMISSIONS_REQUEST = 1;
    private final int STORATGE_PERMISSIONS_REQUEST = 2;

    MapViewMirrored mapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }

    private void init(){
        init_ui();
        init_permissions();
    }

    private void init_ui(){
        mapView = findViewById(R.id.mapview);
    }

    private void init_map(){
//        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        IMapController mapController = mapView.getController();
        mapController.setZoom(19.);
        GeoPoint startPoint = new GeoPoint(41.891789, -87.622865);
        mapController.setCenter(startPoint);
    }

    private void init_permissions(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkAndRequestLocationPermissions() && checkAndRequestStoragePermissions()){
                Log.d(TAG, "Permissions granted");
                init_map();
            }
        }else{
            init_map();
        }
    }

    private boolean checkAndRequestLocationPermissions(){
        int fineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        ArrayList<String> permissionsNeeded = new ArrayList<>();

        if (fineLocationPermission != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (coarseLocationPermission != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (!permissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[permissionsNeeded.size()]), LOCATION_PERMISSIONS_REQUEST);
            return false;
        }

        return true;
    }

    private boolean checkAndRequestStoragePermissions(){
        int readStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        ArrayList<String> permissionsNeeded = new ArrayList<>();

        if (readStoragePermission != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (writeStoragePermission != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[permissionsNeeded.size()]), STORATGE_PERMISSIONS_REQUEST);
            return false;
        }

        return true;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSIONS_REQUEST:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    init_permissions();
                } else {
                    // permission denied, boooom! Disable the
                    // functionality that depends on this permission.
                    //You did not accept the request can not use the functionality.
                    Snackbar.make(getWindow().getDecorView(),"Location permission not granted, click to try again", Snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ActivityCompat.requestPermissions(MainActivity.this, permissions, LOCATION_PERMISSIONS_REQUEST);
                                }
                            }).show();
                }
                break;
            case STORATGE_PERMISSIONS_REQUEST:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    init_permissions();
                } else {
                    // permission denied, boooom! Disable the
                    // functionality that depends on this permission.
                    //You did not accept the request can not use the functionality.
                    Snackbar.make(getWindow().getDecorView(),"Storage permission not granted, click to try again", Snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ActivityCompat.requestPermissions(MainActivity.this, permissions, STORATGE_PERMISSIONS_REQUEST);
                                }
                            }).show();
                }
                break;

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}
