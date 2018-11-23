package com.example.rplrus021.midsemester12rpl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class login extends AppCompatActivity {
    EditText edt_email, edt_password;
    Button btn_login;
    String email, password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Toolbar toolbar;
    private GoogleSignInOptions gso;
    private GoogleSignInClient googleSignInClient;
    private SignInButton signInButton;
    private int RC_SIGN_IN = 1;
    private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                    Intent intent = new Intent(getApplicationContext(), home.class);
                    sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.apply();
                    startActivity(intent);
                    finish();
                }
            }
        });
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_In();
            }
        });
        account = GoogleSignIn.getLastSignedInAccount(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (account != null) {
            Intent intent = new Intent(getApplicationContext(),home.class);
            startActivity(intent);
        }
    }

    private void sign_In() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            account = task.getResult(ApiException.class);
            Intent intent = new Intent(getApplicationContext(),home.class);
            startActivity(intent);

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("TAG", "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }
}
