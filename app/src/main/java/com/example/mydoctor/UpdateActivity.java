package com.example.mydoctor;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UpdateActivity extends AppCompatActivity  {

    private static MyDatabaseHelper myDB;
    private static EditText name_input, age_input, phone_input,address_input,date,symptoms_input, medicine_input, reaction_input, comments_input;

    Button update_button, delete_button;
    TextView date_input,no_data;
    String id, name, age, phone,address,time,symptoms,medicine,reaction,comments;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        String search = getIntent().getExtras().getString("positionvalue","0");
        myDB = new MyDatabaseHelper(UpdateActivity.this);

        name_input = findViewById(R.id.name_input2);
        age_input = findViewById(R.id.age_input2);
        phone_input = findViewById(R.id.phone_input2);
        address_input = findViewById(R.id.address_input2);
        symptoms_input = findViewById(R.id.symptoms_input2);
        medicine_input = findViewById(R.id.medicine_input2);
        reaction_input = findViewById(R.id.reaction_input2);
        comments_input = findViewById(R.id.comments_input2);
        date_input=findViewById(R.id.date_input);

        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);


        setData(search);

        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //And only then we call this
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                if((name_input.getText().toString().equals("")) || age_input.getText().toString().equals("")
                        || phone_input.getText().toString().equals(""))
                {
                    Toast.makeText(UpdateActivity.this, "Please enter the details", Toast.LENGTH_SHORT).show();
                }
                else if(phone_input.getText().toString().length()!=10)
                {
                    Toast.makeText(UpdateActivity.this,"Phone Number invalid",Toast.LENGTH_SHORT).show();
                    phone_input.requestFocus();
                }
                else{
                name = name_input.getText().toString().trim();
                age = age_input.getText().toString().trim();
                address = address_input.getText().toString().trim();
                phone = phone_input.getText().toString().trim();

                symptoms = symptoms_input.getText().toString().trim();
                medicine = medicine_input.getText().toString().trim();
                reaction = reaction_input.getText().toString().trim();
                comments = comments_input.getText().toString().trim();
                myDB.updateData(id, name, age, phone,address,getdate(),symptoms,medicine,reaction,comments);
                myDB.readAllData();
                finish();

                //view chota hai re
            }}
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }
    public void onResume() {
        super.onResume();

    }

    public  void setData(String search) {
        Cursor r = myDB.readData(search);
        while (r.moveToNext()) {
            id = r.getString(0);
            name_input.setText(r.getString(1));
            age_input.setText(r.getString(2));
            phone_input.setText(r.getString(3));
            address_input.setText(r.getString(4));
            date_input.setText(r.getString(5));
            symptoms_input.setText(r.getString(6));
            medicine_input.setText(r.getString(7));
            reaction_input.setText(r.getString(8));
            comments_input.setText(r.getString(9));

            }
            r.close();
//
        }

    static String getdate(){
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh-mm");
        String formattedDate = df.format(c);
        return formattedDate;
    }
    public void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("age") && getIntent().hasExtra("phone") &&
        getIntent().hasExtra("address") && getIntent().hasExtra("symptoms") && getIntent().hasExtra("medicine") &&
        getIntent().hasExtra("reaction") && getIntent().hasExtra("comments") && getIntent().hasExtra("date")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            age = getIntent().getStringExtra("age");
            phone = getIntent().getStringExtra("phone");
            address= getIntent().getStringExtra("address");
            time= getIntent().getStringExtra("date");
            symptoms =getIntent().getStringExtra("symptoms");
            medicine =getIntent().getStringExtra("medicine");
            reaction =getIntent().getStringExtra("reaction");
            comments = getIntent().getStringExtra("comments");
            // Date and Time
            //Setting Intent Data
            name_input.setText(name);
            age_input.setText(age);
            phone_input.setText(phone);
            address_input.setText(address);
            date_input.setText(time);
            reaction_input.setText(reaction);
            comments_input.setText(comments);
            medicine_input.setText(medicine);
            symptoms_input.setText(symptoms);
        }else{
            Toast.makeText(this, "No data Updated", Toast.LENGTH_SHORT).show();
        }
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}


