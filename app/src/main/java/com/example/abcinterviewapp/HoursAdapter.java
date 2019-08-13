package com.example.abcinterviewapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class HoursAdapter extends RecyclerView.Adapter<HoursAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<BranchAtmModel> branchAtmModel;
    private int mSize=0;
    private int mValue=0;

    public HoursAdapter(Context mContext, ArrayList<BranchAtmModel> branchAtmModel,int size, int value) {
        this.mContext = mContext;
        this.branchAtmModel = branchAtmModel;
        mSize = size;
        mValue = value;
    }

    public HoursAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_hour_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        BranchAtmModel model = branchAtmModel.get(mValue);
        Log.d("IME",""+model.getName());
        Log.d("VRIJEDNOST JE",""+mValue);
        ArrayList <WorkingHours> workingHours = model.getWorkingHours();
        if (i<mSize){
            viewHolder.workingTime.setText(workingHours.get(i).getWorkingHours());
            String day= getDay(workingHours.get(i).getDay());
            viewHolder.dayName.setText(day);
        }else {
            viewHolder.workingTime.setText("Closed");
            String day = getDay(i);
            viewHolder.dayName.setText(day);
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    private String getDay(int dayNum) {
        switch (dayNum) {
            case 0:
                return "Monday";
            case 1:
                return "Tuesday";
            case 2:
                return "Wedensday";
            case 3:
                return "Thursday";
            case 4:
                return "Friday";
            case 5:
                return "Saturday";

            case 6:
                return "Sunday";
        }
        return "no Day";
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dayName, workingTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayName = itemView.findViewById(R.id.day);
            workingTime = itemView.findViewById(R.id.working_hours);
        }
    }
}
