package com.example.novelroomverbeta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Novel extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;
    private List<DataBook> EPS;

    public String nameNovel;

    private TextView name,txetdata ,username;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel);

        Intent intent = getIntent();
        DataBook Novel = (DataBook) intent.getSerializableExtra("Novel");

        name = (TextView) findViewById(R.id.nameNovel);
        name.setText("เรื่อง : "+Novel.getNovelname());

        txetdata = (TextView) findViewById(R.id.textView5);
        txetdata.setText("เรื่องย่อ :       "+Novel.getTextdata());

        username = (TextView) findViewById(R.id.name);
        username.setText("ผู้แต่ง : "+Novel.getUsername());


        image = findViewById(R.id.image);
        Glide.with(this).asBitmap().load(Novel.getUrl()).into(image);

        nameNovel = Novel.getNovelname();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        progressDialog = new ProgressDialog(this);
        EPS = new ArrayList<>();
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_EP_UPLOADS);
        //adding an event listener to fetch values
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                progressDialog.dismiss();


                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    DataBook food = postSnapshot.getValue(DataBook.class);

                    if(nameNovel.equals(food.getNovelname())){
                        EPS.add(food);
                    }
                }

                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

        adapter = new EPAdapter(EPS, this);

    }

}
