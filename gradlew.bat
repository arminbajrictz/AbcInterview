
package com.example.igallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class UserPhotoDet extends AppCompatActivity {

    private TextView photoTitleDet,photoPriceDet,descriptionDet,likesNum,stockState;
    private Button backToGallButt;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseStorage mStorage;
    private StorageReference mStorRef;
    private ImageView photoFrameDet;
    private long passedKey;
    private int numLiked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_photo_det);

        ////////////

        passedKey = getIntent().getLongExtra("passedkey",0);
        Log.d("I PASSED ",""+passedKey);

        photoTitleDet = findViewById(R.id.titleTextDetFrag);
        photoPriceDet =findViewById(R.id.pricePhotDetailsFrag);
        descriptionDet=findViewById(R.id.descriptionDetFrag);
        backToGallButt=findViewById(R.id.backToGallButt);
        photoFrameDet=findViewById(R.id.photoFrameDetFrag);
        likesNum=findViewById(R.id.likesNumDetFrag);
        stockState=findViewById(R.id.onStockDetails);

        descriptionDet.setMovementMethod(new ScrollingMovementMethod());

    