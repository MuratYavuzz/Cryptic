package com.example.encrypphotos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class PasswordActivity extends AppCompatActivity {
    EditText password_text;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_password);


        password_text = (EditText) findViewById(R.id.password_text);
        login = (Button) findViewById(R.id.login);
    }

    public void Home(View view) {
        String s = password_text.getText().toString();
        if(s.equals("1731"))
        {
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }
        else
            Toast.makeText(getApplicationContext(),"Wrong Password!",Toast.LENGTH_SHORT).show();
    }
}
