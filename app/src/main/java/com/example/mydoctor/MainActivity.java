package com.example.mydoctor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Serializable {

    RecyclerView recyclerView;
    FloatingActionButton add_button2;
    TextView no_data;

    MyDatabaseHelper myDB;
    ArrayList<String> patient_id, name, age, phone, address, date, symptoms, medicine, reaction, comments;
    CustomAdapter customAdapter;
    ListView userlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_button2 = findViewById(R.id.add_button_patient); /// change directly through xml ok
        no_data = findViewById(R.id.no_data);
        userlist = findViewById(R.id.userlist);
        userlist.setVisibility(View.GONE);

        add_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);

            }
        });

        myDB = new MyDatabaseHelper(MainActivity.this);
        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this, this, patient_id, name, age,
                phone, address, date, symptoms, medicine, reaction, comments);


        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    @Override
    public void onResume() {
        super.onResume();

        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this, this, patient_id, name, age,
                phone, address, date, symptoms, medicine, reaction, comments);


        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                myDB.deleteAllData();
                //Refresh Activity
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {



            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText==null || newText.trim().equals(""))
                {
                    userlist.setVisibility(View.GONE);
                }else
                    userlist.setVisibility(View.VISIBLE);

                final ArrayList<String> userslist = new ArrayList<>();

                for (String user : name) {
                    if (user.toLowerCase().contains(newText.toLowerCase())) {
                        userslist.add(user);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, userslist);
                userlist.setAdapter(adapter);
                userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent mintent = new Intent(MainActivity.this, UpdateActivity.class);
                        mintent.putExtra("positionvalue",userslist.get(position));
                        mintent.putExtra("userkalist", userslist.toString());
                        startActivity(mintent);
                    }
                });
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        MainActivity.super.onBackPressed();
                        moveTaskToBack(true);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    void storeDataInArrays() {

        patient_id = new ArrayList<>();
        name = new ArrayList<>();
        age = new ArrayList<>();
        phone = new ArrayList<>();
        address = new ArrayList<>();
        date = new ArrayList<>();
        symptoms = new ArrayList<>();
        medicine = new ArrayList<>();
        reaction = new ArrayList<>();
        comments = new ArrayList<>();

        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            no_data.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                patient_id.add(cursor.getString(0));
                name.add(cursor.getString(1));
                age.add(cursor.getString(2));
                phone.add(cursor.getString(3));
                address.add(cursor.getString(4));
                date.add(cursor.getString(5));
                symptoms.add(cursor.getString(6));
                medicine.add(cursor.getString(7));
                reaction.add(cursor.getString(8));
                comments.add(cursor.getString(9));

                no_data.setVisibility(View.GONE);
            }
        }
        cursor.close();
    }
}







