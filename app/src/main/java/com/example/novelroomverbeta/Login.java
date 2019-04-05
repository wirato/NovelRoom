package com.example.novelroomverbeta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private TextView register;
    private Button button_login;
    private EditText username,password;
    private DatabaseReference mDatabase;
    private CheckBox checkBox;
    private static boolean re;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        button_login = findViewById(R.id.button_login);


        register = (TextView) findViewById(R.id.register);

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        //CheckBox
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isChecked = checkBox.isChecked ( );
                if ( isChecked ) {
                    Toast toast = Toast.makeText ( Login.this, "จำรหัสผ้าน", Toast.LENGTH_LONG );
                    toast.show ( );
                }
                else if ( ! isChecked )        {
                    Toast toast = Toast.makeText ( Login.this, "ไม่จำรหัสผ้าน", Toast.LENGTH_LONG );
                    toast.show ( );
                }
            }
        });
        final DataRegister remeber = new DataRegister();
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    DataRegister user = snapshot.getValue(DataRegister.class);
                                    String userName = username.getText().toString();
                                    String passWord = password.getText().toString();


                                    if(userName.equals(user.getUsername()) && passWord.equals(user.getPassword())){

                                        DataRegister dataname = new  DataRegister(user.getUsername(),user.geteMail());

                                        writeNewUser(true,userName,passWord);


                                        Intent intent = new Intent(Login.this, MainActivity.class);
                                        Toast toast = Toast.makeText(getApplicationContext(), "ล็อคอินแล้ว", Toast.LENGTH_SHORT);
                                        toast.show();
                                        intent.putExtra("Name", dataname);
                                        startActivity(intent);

//                                        startActivity(new Intent(getApplicationContext(),PageMain.class));
//                                        Toast.makeText(getApplicationContext(),"ล็อคอินแล้ว",Toast.LENGTH_SHORT).show();

                                        //CheckBox
//                                        boolean isChecked = checkBox.isChecked ( );
//                                        if ( isChecked ) {
////                                            re = true;
////                                            DataRegister rE = new DataRegister(true,user.getUsername(),user.getPassword());
////                                            mDatabase.child("remember").push().setValue(rE);
//                                        } else{ re = false;}
//                                        break;
                                    }else {
//                                        re = false;
                                        Toast.makeText(getApplicationContext(), "รหัสผ่านผิดผลาด", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
            }
        }
        );
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                Toast toast = Toast.makeText(getApplicationContext(), "register", Toast.LENGTH_SHORT);
                toast.show();
                startActivity(intent);
            }
        });
    }
    private void writeNewUser(boolean re,String re_username,String re_password) {
        DataRegister data = new DataRegister(re,re_username, re_password);
        mDatabase.child("LoginID").push().setValue(data);
    }
}
