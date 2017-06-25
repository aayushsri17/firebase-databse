package com.example.aayushsrivastava.database.firebase;

/**
 * Created by Aayush Srivastava on 09-06-2017.
 */

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Firebase_Item_Info_Input {

    public String name;
    public List<String> items;
    public String price;
    public String units;




    // Default constructor required for calls to
    // DataSnapshot.getValue(Firebase_Item_info_Input.class)


    public Firebase_Item_Info_Input(){
    }

    public Firebase_Item_Info_Input(String name, List<String> items, String price, String units) {
        this.name = name;
        this.items = items;
        this.price = price;
        this.units = units;
    }
}