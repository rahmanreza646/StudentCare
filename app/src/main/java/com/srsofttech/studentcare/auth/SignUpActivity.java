package com.srsofttech.studentcare.auth;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.srsofttech.studentcare.MainActivity;
import com.srsofttech.studentcare.R;
import com.srsofttech.studentcare.model.User;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    Button register,btn;
    TextView  tv_sign_in_page;
    EditText edit_school_id,edit_student_id,edit_guardian_name,edit_email,edit_phone_no,edit_password;
    ProgressBar progressbar;

    FirebaseDatabase firebaseDatabase;


    DatabaseReference databaseReference;

    User user;


TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        tv_sign_in_page = findViewById(R.id.id_sign_in_page);
        tv_sign_in_page.setOnClickListener(this);
        register = findViewById(R.id.id_btn_signup);
        register.setOnClickListener(this);
        progressbar=findViewById(R.id.id_progressbar2);
        edit_student_id=findViewById(R.id.id_signup_std_id);
        edit_guardian_name=findViewById(R.id.id_signup_guardian_name);
        edit_email=findViewById(R.id.id_signup_guardian_email);
        edit_phone_no=findViewById(R.id.id_signup_guardian_phone);
        edit_password=findViewById(R.id.id_signup_password);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressbar.setVisibility(View.VISIBLE);
               registerUser();


            }
        });

    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.id_sign_in_page){
            finish();

            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));


        }

    }

    @Override
    public void onBackPressed() {
        finish();
        Intent it = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    private void registerUser() {

        final String student_id = edit_student_id.getText().toString().trim();
        final String guardian_name = edit_guardian_name.getText().toString().trim();
        final String email = edit_email.getText().toString().trim();
        final String phone_num = edit_phone_no.getText().toString().trim();
        final String password = edit_password.getText().toString().trim();

        //first we will do the validations

        if (TextUtils.isEmpty(student_id)) {
            edit_student_id.setError("Please enter student id");
            edit_student_id.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(guardian_name)) {
            edit_school_id.setError("Please enter guardian name");
            edit_school_id.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(email)) {
            edit_phone_no.setError("Enter a email address");
            edit_phone_no.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phone_num)) {
            edit_phone_no.setError("Enter a phone number");
            edit_phone_no.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            edit_password.setError("Enter a password");
            edit_password.requestFocus();
            return;
        }


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("user_details");
        user=new User(student_id,guardian_name,email,phone_num,password);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.

                databaseReference.setValue(user);

                progressbar.setVisibility(View.GONE);

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                // after adding this data we are showing toast message.
                Toast.makeText(getApplicationContext(), "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(getApplicationContext(), "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });


    }


}



