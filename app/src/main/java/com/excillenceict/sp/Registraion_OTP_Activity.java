package com.excillenceict.sp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class Registraion_OTP_Activity extends AppCompatActivity {

    private static String TAG ="Registraion_OTP_Activity";

    private CountryCodePicker ccp;
    private EditText phoneText, codeText;
    private Button continueNextButton;
    private String checker="", phoneNumber="";
    RelativeLayout relativeLayout;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResentToken;
    private ProgressDialog loadingBar;
    private TextView jCompLogoName;
    private WebView jUpdateNewsReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registraion_otp);
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        phoneText = findViewById(R.id.phoneText);
        codeText = findViewById(R.id.codeText);
        continueNextButton = findViewById(R.id.continueNextButton);
        relativeLayout = findViewById(R.id.phoneAuth);
        phoneText = findViewById(R.id.phoneText);
//        jCompLogoName = findViewById(R.id.compLogoName);
//        jUpdateNewsReg = findViewById(R.id.updateNewsReg);
        ccp = findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(phoneText);

    }

    public void Login(View view) {
        continueNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (continueNextButton.getText().equals("Submit") || checker.equals("Code Sent")) {

                    String verificationCode = codeText.getText().toString();
                    if (verificationCode.equals("")) {
                        Toast.makeText(Registraion_OTP_Activity.this, "Please write verification first", Toast.LENGTH_SHORT).show();
                    } else {

                        loadingBar.setTitle("Code Verification");
                        loadingBar.setMessage("Pease wait, while we are verifying your code");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();

                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                        signInWithPhoneAuthCredential(credential);
                    }


                } else {
                    phoneNumber = ccp.getFullNumberWithPlus();

                    if (!phoneNumber.equals("")) {

                        loadingBar.setTitle("Phone Number Verification");
                        loadingBar.setMessage("Pease wait, while we are verifying your phone number");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();


                        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, Registraion_OTP_Activity.this, mCallbacks);

                    } else {
                        Toast.makeText(Registraion_OTP_Activity.this, "Please write valid phone number.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Toast.makeText(Registraion_OTP_Activity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
                relativeLayout.setVisibility(View.VISIBLE);
                continueNextButton.setText("Continue");
                codeText.setVisibility(View.GONE);
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                mVerificationId = s;
                mResentToken = forceResendingToken;

                relativeLayout.setVisibility(View.GONE);
                checker = "Code Sent";
                continueNextButton.setText("Submit");
                codeText.setVisibility(View.VISIBLE);
                loadingBar.dismiss();
                Toast.makeText(Registraion_OTP_Activity.this, "Code hase been sent, please check.", Toast.LENGTH_SHORT).show();
            }
        };


    }



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loadingBar.dismiss();
                            Toast.makeText(Registraion_OTP_Activity.this, "Congratulations", Toast.LENGTH_SHORT).show();
                            sendUserToMainActivityes();

                        } else {
                            loadingBar.dismiss();
                            String e  = task.getException().toString();
                            Toast.makeText(Registraion_OTP_Activity.this, "Error: "+e, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    private void sendUserToMainActivityes(){

        finish();
        SharePre sprf = new SharePre(getApplicationContext());
        sprf.secoundItem();

    }
    }
