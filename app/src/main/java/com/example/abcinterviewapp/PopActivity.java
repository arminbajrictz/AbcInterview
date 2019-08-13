package com.example.abcinterviewapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PopActivity extends Activity {

    private TextView workHours;
    private ImageView close;
    private RecyclerView workTime;
    private HoursAdapter adapter;
    public static int intValue=0,size=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Intent mIntent = getIntent();
        intValue = mIntent.getIntExtra("id", -1);
        size=mIntent.getIntExtra("size",0);

        Log.d("TO POP ACTIVITY",""+intValue);


        workHours=findViewById(R.id.workHoursTitle);
        workHours.setShadowLayer(20, 1, 1, Color.BLACK);
        close=findViewById(R.id.closeIcon);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        workTime=findViewById(R.id.recyclerWorkTime);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        workTime.setLayoutManager(linearLayoutManager);
       /* adapter = new HoursAdapter(this,MainActivity.branchesAtmList);*/
        workTime.setAdapter(adapter);
    }
}
