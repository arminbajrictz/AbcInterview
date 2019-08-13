package com.example.abcinterviewapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toolbar;

import java.util.ArrayList;

public class LocationList extends Fragment {

    private EditText search;
    private RecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list,container,false);

        MainActivity.locations.setActivated(false);
        MainActivity.myLocation.setActivated(false);
        MainActivity.list.setActivated(true);

        search=view.findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString().toLowerCase());
            }
        });

        final RecyclerView recyclerView = view.findViewById(R.id.listLocations);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerAdapter(getContext(),MainActivity.branchesAtmList);
        recyclerView.setAdapter(adapter);


        return view;
    }

    private void filter(String text) {
        ArrayList<BranchAtmModel> filteredList = new ArrayList<>();
        for (BranchAtmModel branchAtmModel : MainActivity.branchesAtmList) {
            if (branchAtmModel.getAddress().toLowerCase().contains(text)
            || branchAtmModel.getName().toLowerCase().contains(text)) {
                filteredList.add(branchAtmModel);
            }
        }
        adapter.updateRecyclerView(filteredList);
    }
}
