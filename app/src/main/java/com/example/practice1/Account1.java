package com.example.practice1;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class Account1 extends AppCompatActivity {

    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        t=(TextView) findViewById(R.id.tTitle);
        Typeface customFont = Typeface.createFromAsset(getAssets(),"fonts/Sansation-Bold.ttf");
        t.setTypeface(customFont);
    }

    public void signOut(View v) {

        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(Account1.this,SignIn.class);
        startActivity(i);

    }

    public void verifyEmail(View v)
    {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user.isEmailVerified())
            Toast.makeText(getApplicationContext(), "You are already verified", Toast.LENGTH_SHORT).show();
        else
        {
            user.sendEmailVerification()
                    .addOnCompleteListener(Account1.this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            // Re-enable button
                            findViewById(R.id.bVerifyEmail).setEnabled(true);

                            if (task.isSuccessful()) {
                                Toast.makeText(Account1.this,
                                        "Verification email sent to " + user.getEmail(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Account1.this,
                                        "Failed to send verification email.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        user.reload();




    }
}
