package com.geektech.taskapp.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geektech.taskapp.MainActivity;
import com.geektech.taskapp.R;
import com.geektech.taskapp.Toaster;
import com.geektech.taskapp.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class PhoneActivity extends AppCompatActivity {
    TextView

            textTimer;
    EditText editPhone;
    EditText editCode;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String verivication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        editPhone = findViewById(R.id.editPhone);
        editCode = findViewById(R.id.editCode);
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.d("Tag", "onVerificationCompleted");
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.d("Tag", "onVerificationFailed");
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.d("Tag", "onCodeSent");
                verivication = s;
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Log.d("Tag", "onCodeAutoRetrievalTimeOut");
            }
        };
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(PhoneActivity.this, MainActivity.class));
                    finish();
                    return;
                } else {
                    Toaster.show("Ошибка авторизации" + task.getException().getMessage());
                }
            }
        });
    }

    public void onClick(View view) {
        String phone = editPhone.getText().toString().trim();
        LinearLayout numberField = findViewById(R.id.numberField);
        LinearLayout codeField = findViewById(R.id.codeField);
        numberField.setVisibility(View.INVISIBLE);
        codeField.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone, 60, TimeUnit.SECONDS, this, callbacks);
    }

    public void onCodeClick(View view) {
        String code = editCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            editCode.setError("Cannot be empty.");
            return;
        }
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verivication, code);
        signIn(phoneAuthCredential);
    }
}
