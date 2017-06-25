package com.example.aayushsrivastava.database;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Item_Input_Sqlite extends AppCompatActivity {


    EditText GetName, GetPrice, GetQuantity;
    Button Save_button,Add_name,Remove_name ;

    String Name;
    String Price;
    String Quantity;
    Boolean CheckEditTextEmpty;
    String SQLiteQuery;
    SQLiteDatabase SQLITEDATABASE;
    item_database db;

    EditText editTextView;
    LinearLayout linearLayout2;
    LinearLayout.LayoutParams params;
    int childcount,id;
    List<EditText> allEds= new ArrayList<EditText>();
    String[] strings;
    ArrayList<String> value = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_input_sqlite_2);

       // GetName = (EditText) findViewById(R.id.editText);
        GetPrice = (EditText) findViewById(R.id.editText3);
        GetQuantity = (EditText) findViewById(R.id.editText4);
        GetName= (EditText)findViewById(R.id.editText5);



        Save_button = (Button)findViewById(R.id.button);
        Add_name = (Button)findViewById(R.id.button3);
        Remove_name = (Button)findViewById(R.id.button7);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearlayout2);


        Save_button = (Button) findViewById(R.id.button);


        Save_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                db = new item_database(Item_Input_Sqlite.this);
                SQLITEDATABASE= db.getWritableDatabase();

                Price = GetPrice.getText().toString();
                Quantity = GetQuantity.getText().toString();

                //childcount = linearLayout2.getChildCount();

                allEds = new ArrayList<EditText>();

                strings = new String[allEds.size()];
                for(int i=0; i < allEds.size(); i++){
                    strings[i] = allEds.get(i).getText().toString();
                }





                CheckEditTextIsEmptyOrNot(Name,Price, Quantity);

                if (CheckEditTextEmpty == true) {

                    SQLiteQuery = "INSERT INTO items (name,price,quantity) VALUES('" + strings + "','" + Price + "', '" + Quantity + "');";
                    SQLITEDATABASE.execSQL(SQLiteQuery);
                    Toast.makeText(Item_Input_Sqlite.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();
                    ClearEditTextAfterDoneTask();

                } else {

                    Toast.makeText(Item_Input_Sqlite.this, "Please Fill All the Fields", Toast.LENGTH_LONG).show();
                }


            }
        });

        Add_name.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                childcount = linearLayout2.getChildCount();
                editTextView = new EditText(Item_Input_Sqlite.this);
                allEds.add(editTextView);

                editTextView.setHint("Alternate name");

                    params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1);

                    editTextView.setLayoutParams(params);
                    linearLayout2.addView(editTextView);
                    editTextView.setId(id);



            }
        });


        Remove_name.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                editTextView = new EditText(Item_Input_Sqlite.this);
                editTextView.setHint("Alternate name");

                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,1);

                editTextView.setLayoutParams(params);
              //  linearLayout2.removeAllViews();
            }
        });

    }





    public void CheckEditTextIsEmptyOrNot(String Name, String Price, String Quantity) {

        if (TextUtils.isEmpty(Price) || TextUtils.isEmpty(Quantity)) {

            CheckEditTextEmpty = false;

        } else {
            CheckEditTextEmpty = true;
        }
    }

    public void ClearEditTextAfterDoneTask() {

       // GetName.getText().clear();
        GetPrice.getText().clear();
        GetQuantity.getText().clear();
        editTextView.getText().clear();


    }

}