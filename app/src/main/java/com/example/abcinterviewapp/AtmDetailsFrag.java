package com.example.abcinterviewapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AtmDetailsFrag extends Fragment {

    private TextView atmName,atmLocation,atmAdress,atmWeb;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.atm,container,false);


        bundle=getArguments();
        int number = bundle.getInt("idMarker",-1);;


        atmName=view.findViewById(R.id.branch_title);
        atmLocation=view.findViewById(R.id.address);
        atmAdress=view.findViewById(R.id.addreessTitle);
        atmWeb=view.findViewById(R.id.webSite);

        atmName.setText(MainActivity.branchesAtmList.get(number-1).getName());
        atmLocation.setText(MainActivity.branchesAtmList.get(number-1).getAddress());

        String[] addressOnly = MainActivity.branchesAtmList.get(number-1).getAddress().split(",");
        atmAdress.setText(addressOnly[0]);

        atmWeb.setText(MainActivity.branchesAtmList.get(number-1).getWebsite());


        return view;
    }
}
