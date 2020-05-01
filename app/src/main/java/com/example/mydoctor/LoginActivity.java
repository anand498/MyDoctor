package com.example.mydoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText Name,Password;
    Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Name = findViewById(R.id.username);
        Password = findViewById(R.id.pswdlogin);
        Login = findViewById(R.id.loginbtn);
        Name.setText("admin");
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate( Password.getText().toString(),Name.getText().toString());
            }
        });
        Password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    validate( Password.getText().toString(),Name.getText().toString());

                    handled = true;
                }
                return handled;
            }
        });
    }

    private void validate( String userPassword,String Name){
        if( (userPassword.equals("1234")) && (Name.equals("admin"))){
            Toast.makeText(LoginActivity.this, "Login Success",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(LoginActivity.this, "Wrong Credentials",Toast.LENGTH_SHORT).show();

        }
    }

}
