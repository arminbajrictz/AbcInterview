package com.example.abcinterviewapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String MYLOC = "0";
    private static final String LOC = "1";
    private static final String LIST = "2";
    public static Button myLocation, locations, list;
    public static ArrayList<BranchAtmModel> branchesAtmList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);*/

        branchesAtmList = new ArrayList<>();
        myLocation = findViewById(R.id.myLocationButt);
        locations = findViewById(R.id.locationsButt);
        list = findViewById(R.id.listButt);


//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//        String str = sdf.format(new Date());
//
//        Log.d("SATI JE",""+str);

        String localData = loadJSONFromAsset(getApplicationContext());

        try {
            JSONObject object = new JSONObject(localData);
            String data = object.getString("data");
            JSONArray objectsArray = new JSONArray(data);

            for (int i = 0; i < objectsArray.length(); i++) {
                JSONObject part = objectsArray.getJSONObject(i);
                Log.d("ID OF BRANCHES ARE", "" + part.getString("id"));
                Log.d("NAME", "" + part.getString("name"));
                Log.d("ADDRESS", "" + part.getString("address"));
                Log.d("ID OF BRANCHES ARE", "" + part.getString("email"));
                Log.d("WEBSITE", "" + part.getString("website"));
                Log.d("TYPE", "" + part.getString("type"));
                Log.d("LOCATION", "" + part.getString("location"));


                int id = part.getInt("id");
                String name = part.getString("name");
                String address = part.getString("address");
                String email = part.getString("email");
                String website = part.getString("website");
                String type = part.getString("type");

                String locationData = part.getString("location");
                JSONObject locationObject = new JSONObject(locationData);
                LatLng branchLocation = new LatLng(locationObject.getDouble("lat"), locationObject.getDouble("long"));

                BranchAtmModel branchAtmModel = new BranchAtmModel(id, name, address, email, website, type, branchLocation);


                if (part.has("phone")) {
                    Log.d("PHONE", "" + part.getString("phone"));
                    branchAtmModel.setPhone(part.getString("phone"));
                }


                if (part.has("working_hours")) {
                    Log.d("ID OF BRANCHES ARE", "" + part.getString("working_hours"));
                    String hoursData = part.getString("working_hours");
                    JSONArray time = new JSONArray(hoursData);
                    ArrayList<WorkingHours> workingHoursList = new ArrayList<>();
                    for (int j = 0; j < time.length(); j++) {
                        JSONObject dayInWeek = time.getJSONObject(j);
                        String formatStartHours = convertDate(dayInWeek.getInt("start_hours"));
                        String formatStartMinutes = convertDate(dayInWeek.getInt("start_minutes"));
                        String formatEndHours = convertDate(dayInWeek.getInt("end_hours"));
                        String formatEndMinutes = convertDate(dayInWeek.getInt("end_minutes"));
                        String start = formatStartHours + ":" + formatStartMinutes;
                        String end = formatEndHours + ":" + formatEndMinutes;
                        String formatedTime = start + "-" + end;
                        WorkingHours workingHours = new WorkingHours(formatedTime, dayInWeek.getInt("day"));
                        workingHours.setStartHours(formatStartHours);
                        workingHours.setStartMinutes(formatStartMinutes);
                        workingHours.setEndHours(formatEndHours);
                        workingHours.setEndMinutes(formatEndMinutes);
                        workingHoursList.add(workingHours);
                        Log.d("FORMATIRANO VRIJEME", formatedTime);
                    }
                    branchAtmModel.setWorkingHours(workingHoursList);
                }

                branchesAtmList.add(branchAtmModel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Locations locations = new Locations();
        passIsClicked(locations, false);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_frame, locations, "");
        transaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        createLocationsFragment();
                       /* pressedBut();
                        pinAtmandBranch(MainActivity.branchesAtmList);*/
                    }

                }
            }
        }
    }


    private void createLocationsFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Locations locations = new Locations();
        passIsClicked(locations, false);
        transaction.replace(R.id.fragment_frame, locations, "");
        transaction.commit();
    }

    public void openTab(View view) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        switch (view.getTag().toString()) {
            case MYLOC:
                Locations myLocation = new Locations();
                passIsClicked(myLocation, true);
                Log.d("TAG IS", "" + view.getTag().toString());
                transaction.replace(R.id.fragment_frame, myLocation, "");
                transaction.commit();
                break;
            case LOC:
                Locations locations = new Locations();
                passIsClicked(locations, false);
                transaction.replace(R.id.fragment_frame, locations, "");
                transaction.commit();
                break;
            case LIST:
                transaction.replace(R.id.fragment_frame, new LocationList(), "");
                transaction.commit();
                break;
        }

    }

    private void passIsClicked(Fragment myFrag, Boolean passValue) {
        Bundle isClicked = new Bundle();
        isClicked.putBoolean("clickedMyLoc", passValue);
        myFrag.setArguments(isClicked);
        Log.d("AFETER", "" + passValue.toString());
    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("IBL_data.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public String convertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
    }

}
