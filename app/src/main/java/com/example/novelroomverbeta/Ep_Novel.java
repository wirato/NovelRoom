package com.example.novelroomverbeta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Ep_Novel extends AppCompatActivity {

    private TextView epnovel,textView8,writename,textView10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ep__novel);

        epnovel = (TextView) findViewById(R.id.epnovel);
        textView8 = (TextView) findViewById(R.id.textView8);
        writename = (TextView) findViewById(R.id.writename);

        textView10= (TextView) findViewById(R.id.textView10);
        Intent intent = getIntent();
        DataBook Novel = (DataBook) intent.getSerializableExtra("Novel");

        textView10.setText(Novel.getNovelname());
        epnovel.setText(Novel.getEp());
        textView8.setText("       "+Novel.getEPdataText());
        writename.setText("ผู้แต่ง : "+Novel.getUsername());

    }
}
