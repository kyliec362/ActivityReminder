package com.example.practice1;


import android.app.Activity;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;




public class Friends extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;

    private String uid;

    TextView t;

    ListView lv;
    UsersAdapter aa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();

        t=(TextView)findViewById(R.id.tTitle);
        Typeface customFont = Typeface.createFromAsset(getAssets(),"fonts/Sansation-Bold.ttf");
        t.setTypeface(customFont);

        lv = (ListView) findViewById(R.id.list_view);
        aa = new UsersAdapter(Friends.this,android.R.layout.simple_list_item_1);
        lv.setAdapter(aa);
        aa.notifyDataSetChanged();
        display();



    }

    public void display()
    {

        // Read from the database
        myRef.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    User user = snapshot.getValue(User.class);
                    String UID = user.getUserID();
                    if (UID == null)
                    {
                        Toast.makeText(getApplicationContext(), "null user ID", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (UID.compareTo(mAuth.getCurrentUser().getUid())==0) //current user should not be displayed
                        continue;

                    if (user.getAvailability()) {
                        aa.add(user);



                    }
                }
                //Toast.makeText(getApplicationContext(), aa.getCount() + "" , Toast.LENGTH_SHORT).show();
                t.setText("Available Friends" + " (" + aa.getCount() + ")");


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(Friends.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /*
    public User findUser(final String _ID)
    {

        final User[] u = new User[1];
        // Read from the database
        myRef.child("Users").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    String UID = user.getUserId();
                    Toast.makeText(getApplicationContext(), UID, Toast.LENGTH_SHORT).show();
                    if (UID.compareTo(_ID)==0)
                    { //user found
                        u[0] = user;
                        Toast.makeText(getApplicationContext(), user.getName(), Toast.LENGTH_SHORT).show();


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }});

        if (u[0] == null) {
            Toast.makeText(getApplicationContext(), "Friend not found", Toast.LENGTH_SHORT).show();
            return null;
        }


            return u[0];



    }

    public int display()
    {
        int size=0;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        User u = findUser(user.getUid());

        if (u!=null)
        {
            ArrayList<User> fL = u.getFriendsList();
            if (fL == null)
                return 0;
            aa.addAll(fL);
            size++;
        }
        return size;

    }

    */


    public String recommendActivity(int numF)
    {
        String activity;
        switch (numF)
        {
            case 0:
            {
                activity="gym";
                break;
            }
            case 1:
            {
                activity = "biking";
                break;
            }
            case 2:
            {
                activity = "running";
                break;
            }
            case 3:
            {
                activity = "walking";
                break;
            }
            case 4:
            {
                activity = "volleyball";
                break;
            }
            case 5:
            {
                activity = "basketball";
                break;
            }
            case 6:
            {
                activity = "frisbee";
                break;
            }
            case 7:
            {
                activity = "soccer";
                break;
            }
            default:
            {
                activity = "walking";
                break;
            }

        }

        return activity;
    }


}



