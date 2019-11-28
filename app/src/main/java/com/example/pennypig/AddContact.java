package com.example.pennypig;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.pennypig.Model.DataVault;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddContact extends AppCompatActivity implements VolleyCallback, AdapterView.OnItemSelectedListener {

    private static final String TAG = "AddContact";
    EditText contactToAdd;
    Button verifyContact;
    Button addContact;
    TextView contacts_display;
    String finalname = "";
    String emailList = "";
    String type_add;
    int equalorunequal = 0;
    AlertDialog.Builder builder;
    ArrayList<DataVault.Split> splitArrayList;

    private final String ADD_CONTACT_ACTIVITY_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        contacts_display = findViewById(R.id.displayContacts);
        addContact = findViewById(R.id.Add_toSplit);
        Spinner split_spinner = (Spinner) findViewById(R.id.split_accordingly);

        contactToAdd = findViewById(R.id.add_contact_email_id);
        verifyContact = findViewById(R.id.verification_button);

        splitArrayList = new ArrayList<DataVault.Split>();

        verifyContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyContact(contactToAdd.getText().toString());
            }
        });

        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddContact.this, SplitActivity.class);
                intent.putExtra("contact_name", finalname);
                intent.putExtra("email_list", emailList);
                intent.putExtra("type_add", type_add);
                startActivity(intent);
            }
        });
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.splitAccordingly, R.layout.support_simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        split_spinner.setAdapter(adapter1);
        split_spinner.setOnItemSelectedListener(this);
    }

    public void verifyContact(String email) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "http://18.189.6.243/api/user/ValidateUserEmail"; // to be added
        URL += "?email=" + email;

        final Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);

        VolleyAPIService volleyAPIService = new VolleyAPIService();
        volleyAPIService.callback = this;
        volleyAPIService.volleyPost(URL, params, AddContact.this);

    }

    @Override
    public void onSuccess(String result) {
        Log.d(ADD_CONTACT_ACTIVITY_TAG, "onSuccess: " + result);
        if (!result.equals("\"Invalid User\"")) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            DataVault.UserDetail userDetail = gson.fromJson(result, DataVault.UserDetail.class);


            String name = String.valueOf(userDetail.getName());
            String email = String.valueOf(userDetail.getEmail());
            finalname += name + "\n";
            emailList += email + "\n";


            Log.d(ADD_CONTACT_ACTIVITY_TAG, String.valueOf(userDetail.getId()));

//            Toast.makeText(getApplicationContext(),"Contact To Be Added Verified",Toast.LENGTH_LONG).show();
            contacts_display.setText(finalname);
        } else {
            Toast.makeText(getApplicationContext(), "Invalid User", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        type_add = adapterView.getItemAtPosition(i).toString();
        equalorunequal = i;
        Log.i(TAG, String.valueOf(equalorunequal));
//        Toast.makeText(getApplicationContext(), equalorunequal, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
