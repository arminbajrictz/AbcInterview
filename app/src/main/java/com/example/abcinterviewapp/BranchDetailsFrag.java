package com.example.abcinterviewapp;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BranchDetailsFrag extends Fragment implements View.OnClickListener {

    private Bundle bundle;
    private TextView titleName, location, phone, status, email, website, address;
    private String fulltime;
    private ConstraintLayout hours;
    private String[] addressOnly;
    private int mNumber = 0;
    private int mSize = 0;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.branch, container, false);
        bundle = getArguments();
        if(bundle!=null){
            mNumber = bundle.getInt("idMarker", -1);
            mSize = MainActivity.branchesAtmList.get(mNumber - 1).getWorkingHours().size();
        }
        createViews(mView);
        workingHours();
        setListeners();
        return mView;
    }


    private void setListeners() {
        hours.setOnClickListener(this);
    }


    private void openHoursDialog(View view, int number, int size) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View workingHoursPopUpView = inflater.inflate(R.layout.activity_pop, null);
        final AlertDialog popDialog = new AlertDialog.Builder(view.getContext()).create();
        popDialog.setView(workingHoursPopUpView);

        TextView workHours = workingHoursPopUpView.findViewById(R.id.workHoursTitle);
        ImageView close = workingHoursPopUpView.findViewById(R.id.closeIcon);
        RecyclerView workTime = workingHoursPopUpView.findViewById(R.id.recyclerWorkTime);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        HoursAdapter adapter;

        workHours.setShadowLayer(20, 1, 1, Color.BLACK);
        workTime.setLayoutManager(linearLayoutManager);
        adapter = new HoursAdapter(view.getContext(), MainActivity.branchesAtmList, size, number - 1);
        workTime.setAdapter(adapter);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popDialog.dismiss();
            }
        });
        popDialog.setCanceledOnTouchOutside(false);
        popDialog.show();
    }

    private void createViews(View view) {
        titleName = view.findViewById(R.id.branch_title);
        location = view.findViewById(R.id.address);
        phone = view.findViewById(R.id.phoneNum);
        status = view.findViewById(R.id.status);
        address = view.findViewById(R.id.addreessTitle);
        email = view.findViewById(R.id.email);
        website = view.findViewById(R.id.webSite);
        hours = view.findViewById(R.id.hoursCard);
        titleName.setText(MainActivity.branchesAtmList.get(mNumber - 1).getName());
        location.setText(MainActivity.branchesAtmList.get(mNumber - 1).getAddress());
        phone.setText(MainActivity.branchesAtmList.get(mNumber - 1).getPhone());
        addressOnly = MainActivity.branchesAtmList.get(mNumber - 1).getAddress().split(",");
        address.setText(addressOnly[0]);
        email.setText(MainActivity.branchesAtmList.get(mNumber - 1).getEmail());
        website.setText(MainActivity.branchesAtmList.get(mNumber - 1).getWebsite());
    }

    private void workingHours() {

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        int currentDayNum = -1;

        switch (day) {
            case Calendar.SUNDAY:
                currentDayNum = 6;
                break;
            case Calendar.MONDAY:
                currentDayNum = 0;
                break;
            case Calendar.TUESDAY:
                // etc.
                currentDayNum = 1;
                break;
            case Calendar.WEDNESDAY:
                currentDayNum = 2;
                break;
            case Calendar.THURSDAY:
                currentDayNum = 3;
                break;
            case Calendar.FRIDAY:
                currentDayNum = 4;
                break;
            case Calendar.SATURDAY:
                currentDayNum = 5;
                break;

        }

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        fulltime = sdf.format(new Date());

//        Log.d("SATI JE",""+fulltime);
//
//
//        Log.d("DANAS JE DAN",""+currentDayNum);

        for (int i = 0; i < MainActivity.branchesAtmList.get(mNumber - 1).getWorkingHours().size(); i++) {
            if (MainActivity.branchesAtmList.get(mNumber - 1).getWorkingHours().get(i).getDay() == currentDayNum) {
                Log.d("ima broj dana", "" + MainActivity.branchesAtmList.get(mNumber - 1).getWorkingHours().get(i).getDay());

                if (isOPened(MainActivity.branchesAtmList.get(mNumber - 1), i)) {
                    status.setText("Opened");
                } else {
                    status.setText("Closed");
                }
                break;
            } else {
                status.setText("Closed");
            }


        }
    }


    private boolean isOPened(BranchAtmModel branchAtmModel, int position) {
        String[] splitTime = fulltime.split(":");
        String currentHour = splitTime[0];
        String currentMinutes = splitTime[1];
        String current = currentHour + currentMinutes;
        Log.d("SAMO SAT", "" + currentHour);
        Log.d("SAMO MIN", "" + currentMinutes);


        Log.d("ISCIJEPANI SATI", "" + branchAtmModel.getWorkingHours().get(position).getStartHours());
        Log.d("ISCIJEPANE MINUTE", "" + branchAtmModel.getWorkingHours().get(position).getStartMinutes());
        Log.d("ISCIJEPANI SATI KRAJ", "" + branchAtmModel.getWorkingHours().get(position).getEndHours());
        Log.d("ISCIJEPANE MINUTE KRAJ", "" + branchAtmModel.getWorkingHours().get(position).getEndMinutes());

        String start = branchAtmModel.getWorkingHours().get(position).getStartHours() + branchAtmModel.getWorkingHours().get(position).getStartMinutes();
        String end = branchAtmModel.getWorkingHours().get(position).getEndHours() + branchAtmModel.getWorkingHours().get(position).getEndMinutes();
        int startValue = Integer.parseInt(start);
        int endValue = Integer.parseInt(end);
        int currentValue = Integer.parseInt(current);
        Log.d("start value je", "" + startValue);
        Log.d("end value je", "" + endValue);
        Log.d("current value je", "" + currentValue);

        if (currentValue >= startValue && currentValue <= endValue) {
            return true;
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hoursCard:
                openHoursDialog(mView, mNumber, mSize);
                break;
            default:
                // do nothing
                break;
        }
    }
}



