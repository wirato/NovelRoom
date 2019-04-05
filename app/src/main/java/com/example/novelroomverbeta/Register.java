package com.example.novelroomverbeta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity {


    android.widget.EditText username,e_mail,password,confirm_password;
    android.widget.Button button_register;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.username);
        e_mail = (EditText) findViewById(R.id.e_mail);
        password = (EditText) findViewById(R.id.password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        button_register = (Button) findViewById(R.id.button_register);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        button_register.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                String user = username.getText().toString();
                String eMail = e_mail.getText().toString();
                String password1 = password.getText().toString();
                String password2 = confirm_password.getText().toString();

                if(password1.equals(password2)){

                    if(eMail.endsWith("hotmail.com") || eMail.endsWith("hotmail.co.th") || eMail.endsWith("gmail.com") || eMail.endsWith("outlook.com") || eMail.endsWith("outlook.co.th") || eMail.endsWith("ubu.ac.th")) {
                        writeNewUser(user,eMail,password1);

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        Toast.makeText(getApplicationContext(), "สมัครไอดีเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "อีเมลหรือรหัสผ่าน ไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"รหัสผ่านไม่ตรงกัน",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void writeNewUser(String Username,String eMail, String Password) {
        DataRegister data = new DataRegister(Username,eMail, Password);
        mDatabase.child("users").push().setValue(data);
    }
}