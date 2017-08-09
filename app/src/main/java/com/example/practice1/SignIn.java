package com.example.practice1;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.firebase.database.*;

import java.util.concurrent.TimeUnit;


public class SignIn extends AppCompatActivity implements View.OnClickListener  {

    private static final String TAG = "EmailPassword";


    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mNameField;

    FirebaseDatabase database;
    DatabaseReference myRef;

    TextView t;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Views
        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);
        mNameField=(EditText)findViewById(R.id.field_name);

        // Buttons
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.email_create_account_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.verify_email_button).setOnClickListener(this);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        mAuth = FirebaseAuth.getInstance();

        t=(TextView) findViewById(R.id.tTitle);
        Typeface customFont = Typeface.createFromAsset(getAssets(),"fonts/Sansation-Bold.ttf");
        t.setTypeface(customFont);

    }


    private void createAccount(final String email, String password, final String name)
    {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }


        if (TextUtils.isEmpty(name)) {
            mNameField.setError("Required.");
            return;
        }

        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            hideProgressDialog();
                            //updateName(user,name);
                            Toast.makeText(SignIn.this, "Create account successful.",Toast.LENGTH_SHORT).show();
                            //writeNewUser(user.getUid(),name);
                            addUserToDatabase(user,name);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignIn.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                            hideProgressDialog();
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]


    }

    private void writeNewUser(String userId, String name) {
        if (userId != null && name !=null)
        {
            User user = new User(name,userId,true);
            myRef.child("Users").child(userId).setValue(new User());

        }

    }

    public void addUserToDatabase(FirebaseUser user, String name)
    {

        if (user != null && name != null)
        {
            String uid = user.getUid();
            myRef.child("Users").child(uid).child("name").setValue(name);
            myRef.child("Users").child(uid).child("userID").setValue(uid);
            myRef.child("Users").child(uid).child("availability").setValue(true);

        }

    }


    public void hideSoftKeyboard(View v) {

        Activity activity = this;
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);

    }



    private void signIn(String email, String password) {

        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            hideProgressDialog();
                            Intent intent = new Intent(SignIn.this,Quickview.class);
                            startActivity(intent);


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignIn.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                            hideProgressDialog();
                        }

                        hideProgressDialog();
                    }
                });
        // [END sign_in_with_email]

    }



    public void showProgressDialog() {

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();

    }

    public void hideProgressDialog() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

    }



    private boolean validateForm() {

        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password))
        {
            mPasswordField.setError("Required.");
            valid = false;
        }
        else
        {
            mPasswordField.setError(null);
        }

        return valid;
    }





    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.email_create_account_button) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString(), mNameField.getText().toString());
        } else if (i == R.id.email_sign_in_button) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
    }


}





