package com.example.pennypig;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements VolleyCallback{

    private final String MAIN_ACTIVITY_TAG = "MainActivity";
    Button login;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);

        login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyLogin(email.getText().toString(), password.getText().toString());
                // Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                // startActivity(intent);
            }
        });
    }

    public void companyLogin(String email, String password) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "https://10.0.2.2:5001/api/user/GetAllUsers";
        final Map<String, String> params = new HashMap<String, String>();
        params.put("name", email);
        params.put("pwd", password);

        VolleyAPIService volleyAPIService = new VolleyAPIService();
        volleyAPIService.callback = this;
        volleyAPIService.volleyGet(URL, MainActivity.this);
        // volleyAPIService.volleyPost(URL, params, MainActivity.this);
    }

    @Override
    public void onSuccess(String result) {
        Log.d("ATKGIVOLLY", "onSuccess: " + result);
        if (!result.isEmpty()) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            // Log.d("MAIN_ACTIVITY_TAG", String.valueOf(companyLogin.getId()));
            // Intent userLoginActivity = new Intent(MainActivity.this, UserLogin.class);
            // startActivity(userLoginActivity);
        } else {
            AlertDialog.Builder login_failed = new AlertDialog.Builder(MainActivity.this);
            login_failed.setMessage("Login Failed, invalid credentials")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog alert = login_failed.create();
            alert.show();
        }
    }
}
