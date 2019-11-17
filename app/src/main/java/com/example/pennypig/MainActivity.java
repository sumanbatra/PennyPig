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
import com.example.pennypig.Model.DataVault;
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
            }
        });
    }

    public void companyLogin(String email, String password) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "http://18.189.6.243/api/user/ValidateUser";
        URL += "?email=" + email + "&password=" + password;

        final Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);

        VolleyAPIService volleyAPIService = new VolleyAPIService();
        volleyAPIService.callback = this;
        volleyAPIService.volleyPost(URL, params, MainActivity.this);
    }

    @Override
    public void onSuccess(String result) {
        Log.d(MAIN_ACTIVITY_TAG, "onSuccess: " + result);
        if (!result.equals("Invalid User")) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            DataVault.UserDetail userDetail = gson.fromJson(result, DataVault.UserDetail.class);

            String userid = String.valueOf(userDetail.getId());
            String name = String.valueOf(userDetail.getEmail());
            String email = String.valueOf(userDetail.getName());

            Log.d(MAIN_ACTIVITY_TAG, String.valueOf(userDetail.getId()));

            Intent intent = new Intent(MainActivity.this, ExpenseMaster.class);
            startActivity(intent);
        }
        else {
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
