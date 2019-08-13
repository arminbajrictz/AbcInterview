package com.example.abcinterviewapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


public class BranchAtmDetails extends AppCompatActivity {

    private ImageView back;
    private static final String BRANCH = "branch";
    private static final String ATM = "atm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_atm_details);

        Toolbar toolbar = findViewById(R.id.toolbarWithIcon);
        setSupportActionBar(toolbar);
        back=toolbar.findViewById(R.id.backButt);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(main);
            }
        });


        SharedPreferences sp = getSharedPreferences("passedId", Activity.MODE_PRIVATE);
        int myIntValue = sp.getInt("id", 0);
        Log.d("ovo je",""+myIntValue);

        switch (MainActivity.branchesAtmList.get(myIntValue-1).getType()) {
            case BRANCH :
                BranchDetailsFrag branchDetailsFrag = new BranchDetailsFrag();
                initialize(branchDetailsFrag,myIntValue);
                break;
            case ATM:
                AtmDetailsFrag atmDetailsFrag = new AtmDetailsFrag();
                initialize(atmDetailsFrag,myIntValue);
                break;
        }
    }


    private void initialize (Fragment fragment, int markerId) {
        Bundle mbundle = new Bundle();
        mbundle.putInt("idMarker",markerId);
        fragment.setArguments(mbundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.details_frame,fragment,"frag");
        transaction.commit();
    }
}
