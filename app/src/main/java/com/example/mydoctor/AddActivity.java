package com.example.mydoctor;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    EditText patient_input, age_input, phone_input,address_input,symptoms_input,medicine_input,reaction_input,comments_input;
    Button add_button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        patient_input = findViewById(R.id.name_input);
        age_input = findViewById(R.id.age_input);
        phone_input = findViewById(R.id.phone_input);
        address_input=findViewById(R.id.address_input);
        symptoms_input=findViewById(R.id.symptoms_input);
        medicine_input=findViewById(R.id.medicine_input);
        reaction_input=findViewById(R.id.reaction_input);
        comments_input=findViewById(R.id.comments_input);

        add_button1 = findViewById(R.id.add_button);
        add_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((patient_input.getText().toString().equals("")) || age_input.getText().toString().equals("")
                || phone_input.getText().toString().equals(""))
                {
                    Toast.makeText(AddActivity.this, "Please enter the details", Toast.LENGTH_SHORT).show();
                }
                else if(phone_input.getText().toString().length()!=10)
                {
                    Toast.makeText(AddActivity.this,"Phone Number Invalid",Toast.LENGTH_SHORT).show();
                    phone_input.requestFocus();
                }
                    else{
                    MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                    myDB.readAllData();
                    myDB.addPatient(patient_input.getText().toString().trim(),
                            age_input.getText().toString().trim(),
                            phone_input.getText().toString().trim(), address_input.getText().toString().trim(),
                            symptoms_input.getText().toString().trim(), medicine_input.getText().toString().trim()
                            , reaction_input.getText().toString().trim(), comments_input.getText().toString().trim());
                    myDB.readAllData();
                    finish();
                }
            }
        });
    }
}
