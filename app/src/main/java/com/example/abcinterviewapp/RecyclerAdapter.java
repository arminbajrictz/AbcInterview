package com.example.abcinterviewapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static final String BRANCH = "branch";
    private static final String ATM = "atm";
    private Context mContext;
    private ArrayList<BranchAtmModel> myList;

    public RecyclerAdapter() {
    }

    public RecyclerAdapter(Context mContext, ArrayList<BranchAtmModel> myList) {
        this.mContext = mContext;
        this.myList = myList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final BranchAtmModel branchAtmModel = myList.get(i);
        viewHolder.name.setText(branchAtmModel.getName());
        viewHolder.address.setText(branchAtmModel.getAddress());
        switch (branchAtmModel.getType()) {
            case BRANCH :
                viewHolder.iconThumbnail.setImageResource(R.drawable.ic_branch);
                break;
            case ATM:
                viewHolder.iconThumbnail.setImageResource(R.drawable.ic_atm);
                break;
        }

        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("passedIdNum",branchAtmModel.getId());

                Locations locations = new Locations();
                locations.setArguments(bundle);
                FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_frame,locations);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public void updateRecyclerView(ArrayList<BranchAtmModel> filteredList) {
        myList=filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView iconThumbnail;
        private TextView name, address;
        private ConstraintLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconThumbnail = itemView.findViewById(R.id.iconImage);
            name = itemView.findViewById(R.id.nameTitle);
            address=itemView.findViewById(R.id.addressTitle);
            container=itemView.findViewById(R.id.cardsContainer);
        }
    }
}
