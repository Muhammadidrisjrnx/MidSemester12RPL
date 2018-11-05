package com.example.rplrus021.midsemester12rpl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class login extends AppCompatActivity {
    EditText edt_email, edt_password;
    Button btn_login;
    String email, password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_password = (EditText) findViewById(R.id.edt_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edt_email.getText().toString();
                password = edt_password.getText().toString();
                if (email.isEmpty() && password.isEmpty()) {
                    edt_email.setError("Please write email");
                    edt_password.setError("Please write password");
                } else if (email.equals("zero") && password.equals("admin")) {
                    Intent intent = new Intent(getApplicationContext(),home.class);
                    sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("email",email);
                    editor.putString("password",password);
                    editor.apply();
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
