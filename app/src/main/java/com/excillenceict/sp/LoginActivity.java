package com.excillenceict.sp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText name,pass;
    private Button login;

    @Override
    protected void onStart() {
        super.onStart();
        SharePre sharePre = new SharePre(getApplicationContext());
        sharePre.firsTime();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name=findViewById(R.id.userName);
        pass=findViewById(R.id.pass);
        login =findViewById(R.id.login_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass.getText().toString().equals("a") && name.getText().toString().equals("a")){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
//                    finish();
//                    SharePre sprf = new SharePre(getApplicationContext());
//                    sprf.secoundItem();
                }else {
                    Toast.makeText(LoginActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}