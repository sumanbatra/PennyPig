package com.example.pennypig;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.pennypig.Model.DataVault;
import com.example.pennypig.SharedPreference.SaveSharedPreference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements VolleyCallback, SignUpCallback {

    EditText signupEmail;
    EditText signupName;
    EditText signupPassword;
    EditText signupRepassword;
    TextView signupErrorText;
    Button signup;

    String email;
    String name;
    String password;
    String rePassword;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signupEmail = (EditText) findViewById(R.id.signup_email);
        signupName = (EditText) findViewById(R.id.signup_name);
        signupPassword = (EditText) findViewById(R.id.signup_password);
        signupRepassword = (EditText) findViewById(R.id.signup_repassword);
        signup = (Button) findViewById(R.id.signup_button);
        signupErrorText = (TextView) findViewById(R.id.signup_error_text);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signupErrorText.setVisibility(View.INVISIBLE);
                progressDialog = new ProgressDialog(SignUpActivity.this);
                progressDialog.setTitle("Adding User");
                progressDialog.setMessage("Wait while we add you to Penny Pig...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                email = signupEmail.getText().toString();
                name = signupName.getText().toString();
                password = signupPassword.getText().toString();
                rePassword = signupRepassword.getText().toString();

                if(email.contains("@")) {
                    RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this);
                    String URL = "http://18.189.6.243/api/user/ValidateUserEmail";
                    URL += "?email=" + email;

                    final Map<String, String> params = new HashMap<String, String>();
                    params.put("email", email);

                    VolleyAPIService volleyAPIService = new VolleyAPIService();
                    volleyAPIService.callback = SignUpActivity.this;
                    volleyAPIService.volleyPost(URL, params, SignUpActivity.this);
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onSuccess(String result) {
        if (result.equals("\"Invalid User\"")) {
            if(password.length() >= 6) {
                if(!password.equals(rePassword)) {
                    Toast.makeText(SignUpActivity.this, "Password and Confirm password mismatch", Toast.LENGTH_SHORT).show();
                }
                else {
                    RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this);
                    String URL = "http://18.189.6.243/api/user/InsertUser";
                    URL += "?email=" + email + "&password=" + password + "&name=" + name;

                    final Map<String, String> params = new HashMap<String, String>();
                    params.put("email", email);

                    VolleyAPIService volleyAPIService = new VolleyAPIService();
                    volleyAPIService.callback = null;
                    volleyAPIService.signUpCallback = SignUpActivity.this;
                    volleyAPIService.volleyPost(URL, params, SignUpActivity.this);
                    progressDialog.dismiss();
                }
            }
            else {
                Toast.makeText(SignUpActivity.this, "password length should be 6 or greater than 6", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }
        else {
            signupErrorText.setTextColor(Color.RED);
            signupErrorText.setText("Email id is already taken");
            signupErrorText.setVisibility(View.VISIBLE);
            progressDialog.dismiss();
        }
    }

    @Override
    public void onSignupSuccess(String result) {
        if(result.equals("1")) {
            Toast.makeText(SignUpActivity.this, "User added successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(SignUpActivity.this, "Error in adding user. Please try after sometime", Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();
    }
}
