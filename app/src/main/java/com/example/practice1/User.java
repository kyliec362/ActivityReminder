package com.example.practice1;


import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.database.IgnoreExtraProperties;



@IgnoreExtraProperties
public class User {

    public String name;
    public String userID;
    public boolean availability;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String n, String uid, boolean b) {
        this.name = n;
        this.userID = uid;
        this.availability=b;
    }

    public String getName()
    {
        return name;
    }
    public Boolean getAvailability()
    {
        return availability;
    }
    public String getUserID() {
        return userID;
    }
    //public void setName(String n) {name=n; }
    //public void setUserId(String s) {userID=s;}
    public void setAvailability(boolean b) {availability=b; }
}



