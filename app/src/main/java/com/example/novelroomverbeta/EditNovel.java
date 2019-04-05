package com.example.novelroomverbeta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditNovel extends AppCompatActivity {

    private TextView textView6;
    private Button button;
    private EditText editText,autoCompleteTextView;
    public String Noveln,user;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_novel);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        DataBook Novel = (DataBook) intent.getSerializableExtra("Novel");

        textView6 = (TextView) findViewById(R.id.textView6);
        textView6.setText(Novel.getNovelname());

        Noveln = Novel.getNovelname();
        user = Novel.getUsername();

        button = (Button) findViewById(R.id.button);

        editText = (EditText) findViewById(R.id.editText);
        autoCompleteTextView = (EditText) findViewById(R.id.autoCompleteTextView);

        button.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                String ep = editText.getText().toString();
                String epDtat = autoCompleteTextView.getText().toString();

                writeNewUser(Noveln,ep,epDtat,user,0);
                Toast.makeText(getApplicationContext(), "บันทึกแล้ว", Toast.LENGTH_SHORT).show();
                editText.setText("");
            }
        });
    }

    private void writeNewUser(String Novelname,String ep, String dataText,String user,int a) {
        DataBook data = new DataBook(Novelname,ep,dataText,user,a);
        mDatabase.child("EP_Novel").push().setValue(data);
    }
}
