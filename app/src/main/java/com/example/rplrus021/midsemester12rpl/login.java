package com.example.rplrus021.midsemester12rpl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class login extends AppCompatActivity {
    EditText edt_email, edt_password;
    Button btn_login;
    String email, password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Toolbar toolbar;
    private GoogleSignInOptions gso;
    private GoogleSignInClient googleSignInClient;
    private SignInButton signInButton_google;
    private int RC_SIGN_IN = 1;
    private GoogleSignInAccount account;
    private com.facebook.login.widget.LoginButton signInButton_fb;
    private CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FacebookSdk.sdkInitialize(getApplicationContext());
       // hash_key_facebook();
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
        signInButton_google = (SignInButton) findViewById(R.id.sign_in_button_google);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_In_google();
            }
        });
        account = GoogleSignIn.getLastSignedInAccount(this);
        signInButton_fb = (com.facebook.login.widget.LoginButton)findViewById(R.id.sign_in_button_fb);
        signInButton_fb.setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();
        signInButton_fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                Profile profile = Profile.getCurrentProfile();
                display_name(profile);
//                Toast.makeText(getApplicationContext(),profile.getName(),Toast.LENGTH_SHORT).show();
//                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    private void display_name(Profile profile) {
        if (profile!=null){
            Toast.makeText(getApplicationContext(),profile.getName(),Toast.LENGTH_SHORT).show();
        }
    }

    private void hash_key_facebook() {
            try {
                PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
                for (Signature signature : info.signatures) {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    String hashKey = new String(Base64.encode(md.digest(), 0));
                    Log.i("TAG", "printHashKey() Hash Key: " + hashKey);
                }
            } catch (NoSuchAlgorithmException e) {
                Log.e("TAG", "printHashKey()", e);
            } catch (Exception e) {
                Log.e("TAG", "printHashKey()", e);
            }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (account != null) {
            Intent intent = new Intent(getApplicationContext(), home.class);
            startActivity(intent);
            finish();
        }
    }

    private void sign_In_google() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }else {
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            account = task.getResult(ApiException.class);
            Intent intent = new Intent(getApplicationContext(), home.class);
            startActivity(intent);
            finish();
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
