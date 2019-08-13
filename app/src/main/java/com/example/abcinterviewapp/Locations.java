package com.example.abcinterviewapp;



import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;

public class Locations extends Fragment {

    private LocationManager locationManager;
    private boolean passed;
    private Bundle mBundle;
    private GoogleMap mMap;
    private LocationListener locationListener;
    private static final String BRANCH = "branch";
    private static final String ATM = "atm";
    private BitmapDescriptor branchAtmicon;
    private int numberId;

    public Locations() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mBundle = getArguments();
        passed = mBundle.getBoolean("clickedMyLoc");
        numberId = mBundle.getInt("passedIdNum", 0);


        Log.d("Passed", "" + passed);


        if (passed == false) {
            MainActivity.locations.setActivated(true);
            MainActivity.myLocation.setActivated(false);
            MainActivity.list.setActivated(false);
        } else {
            MainActivity.locations.setActivated(false);
            MainActivity.myLocation.setActivated(true);
            MainActivity.list.setActivated(false);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);
        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        //Start



        if ( ContextCompat.checkSelfPermission( getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {

            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {

                            Log.d("Clicked id marker is", "ffff" + marker.getTag());
                            String idFromObject = String.valueOf(marker.getTag());
                            int idFromString = Integer.parseInt(idFromObject);
                            if (idFromString != -1) {
                                openDetails(idFromString);
                            }
                        }
                    });

                    locationListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    };

                    if (Build.VERSION.SDK_INT < 23) {
                        pressedBut();
                        pinAtmandBranch(MainActivity.branchesAtmList);
                    } else {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1);

                        } else {
                            pressedBut();
                            pinAtmandBranch(MainActivity.branchesAtmList);
                        }
                    }
                }
            });
        }else{
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},1);

            } else {
                pressedBut();
                pinAtmandBranch(MainActivity.branchesAtmList);
            }
        }

        return view;
    }




    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        pressedBut();
                        pinAtmandBranch(MainActivity.branchesAtmList);
                    }

                }
            }
        }
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 1) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                    pressedBut();
//                    pinAtmandBranch(MainActivity.branchesAtmList);
//                }
//            }
//        }
//    }

    private void showBosnia() {
        LatLng bosnia = new LatLng(43.803637, 17.770266);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bosnia, 7));
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    private void pinUserPosition(Location location) {
        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_user);
        Marker usersMarker = mMap.addMarker(new MarkerOptions().position(currentLocation).title("Users Location").icon(icon));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        usersMarker.setTag(-1);

    }

    private void pinAtmandBranch(ArrayList<BranchAtmModel> branchAtmModelList) {
        for (int i = 0; i < branchAtmModelList.size(); i++) {
            BranchAtmModel branchAtmModel = branchAtmModelList.get(i);
            switch (branchAtmModel.getType()) {
                case BRANCH:
                    branchAtmicon = BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_branch);
                    break;
                case ATM:
                    branchAtmicon = BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_atm);
                    break;
            }

            Marker marker = mMap.addMarker(new MarkerOptions().position(branchAtmModel.getLocation()).icon(branchAtmicon)
                    .title(branchAtmModel.getName() + "\n" + branchAtmModel.getAddress()));
            marker.setTag(branchAtmModelList.get(i).getId());
            Log.d("WHILE PINNING", "" + String.valueOf(branchAtmModelList.get(i).getId()));
        }
    }

    private void showSpecificMarker(LatLng latLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    private void updateLocation() {
        pinUserPosition(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    private void openDetails(int markerId) {
        SharedPreferences sp = this.getActivity().getSharedPreferences("passedId", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("id", markerId);
        editor.commit();
        Intent intent = new Intent(getContext(), BranchAtmDetails.class);
        startActivity(intent);
    }


    private void pressedBut() {

        if (numberId != 0) {
            LatLng specLatLang = MainActivity.branchesAtmList.get(numberId - 1).getLocation();
            showSpecificMarker(specLatLang);
        } else {
            if (!passed) {
                showBosnia();
            } else {
                updateLocation();
            }
        }
    }

    public void checkButtonState(){
        if (!passed) {
            showBosnia();
        } else {
            updateLocation();
        }
    }
}
