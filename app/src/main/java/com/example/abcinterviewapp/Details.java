package com.example.abcinterviewapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Details  extends Fragment {

    private int idToDet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details,container,false);

        Bundle mBundle = getArguments();
        idToDet=mBundle.getInt("markerId",0);
        Log.d("in details",""+idToDet);

        return view;
    }
}
