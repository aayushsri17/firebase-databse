package com.example.aayushsrivastava.database.firebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aayushsrivastava.database.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseMainActivity extends AppCompatActivity {

        private static final String TAG = FirebaseMainActivity.class.getSimpleName();
        private TextView txtDetails;
        private EditText inputName, inputAltname1,inputAltname2,inputAltname3,inputPrice,inputUnits;
        private Button btnSave;
        private DatabaseReference mFirebaseDatabase;
        private FirebaseDatabase mFirebaseInstance;

        private String dataid;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_firebase_main);

            // Displaying toolbar icon
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);

            txtDetails = (TextView) findViewById(R.id.txt_user);
            inputName = (EditText) findViewById(R.id.name);
            inputAltname1 = (EditText) findViewById(R.id.altname1);
            inputAltname2 = (EditText) findViewById(R.id.altname2);
            inputAltname3 = (EditText) findViewById(R.id.altname3);
            inputPrice = (EditText) findViewById(R.id.price);
            inputUnits = (EditText) findViewById(R.id.units);

            btnSave = (Button) findViewById(R.id.btn_save);

            mFirebaseInstance = FirebaseDatabase.getInstance();

            // get reference to 'users' node
            mFirebaseDatabase = mFirebaseInstance.getReference("users");

            // store app title to 'app_title' node
            mFirebaseInstance.getReference("app_title").setValue("Realtime Database");

            // app_title change listener
            mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.e(TAG, "App title updated");

                    String appTitle = dataSnapshot.getValue(String.class);

                    // update toolbar title
                    getSupportActionBar().setTitle(appTitle);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.e(TAG, "Failed to read app title value.", error.toException());
                }
            });

            // Save / update the user
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = inputName.getText().toString();
                    String altname1 = inputAltname1.getText().toString();
                    String altname2 = inputAltname2.getText().toString();
                    String altname3 = inputAltname3.getText().toString();
                    String price = inputPrice.getText().toString();
                    String units = inputUnits.getText().toString();
                     Log.e(TAG, "User data is changed!" + name + ", " + altname1+","+ altname2+","+ altname3 + ", " + price+","+ units);


                    // Check for already existed userId
                    if (TextUtils.isEmpty(dataid)) {
                        createUser(name, altname1,altname2,altname3,price,units);
                    } else {
                        updateUser(name, altname1,altname2,altname3,price,units);
                    }
                }
            });

            toggleButton();
        }

        // Changing button text
        private void toggleButton() {
            if (TextUtils.isEmpty(dataid)) {
                btnSave.setText("Save");
            } else {
                btnSave.setText("Update");
            }
        }

        /**
         * Creating new user node under 'users'
         */
        private void createUser(String name,  String altname1,  String altname2,  String altname3, String price, String units) {
            // TODO
            // In real apps this userId should be fetched
            // by implementing firebase auth
            Log.e(TAG, "User data is changed!" + name + ", " + altname1+","+ altname2+","+ altname3 + ", " + price+","+ units);


            if (TextUtils.isEmpty(dataid)) {
                dataid = mFirebaseDatabase.push().getKey();
            }
            List<String> items= new ArrayList<String>();
            items.add(altname1);
            items.add(altname2);
            items.add(altname3);
            Firebase_Item_Info_Input data = new Firebase_Item_Info_Input(name,items,price,units);

            mFirebaseDatabase.child("newItem").setValue(data);

            addUserChangeListener();
        }

        /**
         * User data change listener
         */
        private void addUserChangeListener() {
            // User data change listener
            mFirebaseDatabase.child(dataid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Firebase_Item_Info_Input data = dataSnapshot.getValue(Firebase_Item_Info_Input.class);

                    // Check for null
                    if (data == null) {
                        Log.e(TAG, "User data is null!");
                        return;
                    }

                  //  Log.e(TAG, "User data is changed!" + data.name + ", " + data.altname1+","+ data.altname2+","+ data.altname3 + ", " + data.price+","+ data.units);

                    // Display newly updated name and email
                  //  txtDetails.setText(data.name + ", " + data.altname1+","+ data.altname2+","+ data.altname3 + ", " + data.price+","+ data.units);

                    // clear edit text
                    inputUnits.setText("");
                    inputPrice.setText("");
                    inputAltname3.setText("");
                    inputAltname2.setText("");
                    inputAltname1.setText("");
                    inputName.setText("");

                    toggleButton();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.e(TAG, "Failed to read user", error.toException());
                }
            });
        }

        private void updateUser(String name, String altname1, String altname2, String altname3, String price, String units) {
            // updating the user via child nodes
            if (!TextUtils.isEmpty(name))
                mFirebaseDatabase.child(dataid).child("name").setValue(name);

           if (!TextUtils.isEmpty(altname1))
                mFirebaseDatabase.child(dataid).child("altname1").setValue(altname1);

            if (!TextUtils.isEmpty(altname2))
                mFirebaseDatabase.child(dataid).child("altname2").setValue(altname2);

            if (!TextUtils.isEmpty(altname3))
                mFirebaseDatabase.child(dataid).child("altname3").setValue(altname3);

            if (!TextUtils.isEmpty(price))
                mFirebaseDatabase.child(dataid).child("price").setValue(price);

            if (!TextUtils.isEmpty(units))
                mFirebaseDatabase.child(dataid).child("units").setValue(units);


        }
    }