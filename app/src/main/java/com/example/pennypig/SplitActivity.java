package com.example.pennypig;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.pennypig.Helpers.DateHelper;
import com.example.pennypig.Model.DataVault;
import com.example.pennypig.SharedPreference.SaveSharedPreference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SplitActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, ExpenseCallback, VolleyCallback {

    String contact_name,final_contact_name = "";
    String type_add;
    String emailList;
    ArrayList<DataVault.Split> splitArrayList;
    Button submitButton;
    EditText amount;
    Spinner cateo_spinner;
    String[] emailArray;

    protected final String ACTIVITY_NAME = "SplitActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split);

        splitArrayList = new ArrayList<DataVault.Split>();

        TextView textView = (TextView) findViewById(R.id.split);
        amount = (EditText) findViewById(R.id.split_amount);
        cateo_spinner = (Spinner) findViewById(R.id.category_array);
        cateo_spinner = (Spinner) findViewById(R.id.category_array);
        TextView nameTextView = findViewById(R.id.Contacts_added);
        TextView split_accordingly_text = findViewById(R.id.split_accordingly_text);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.categories, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        cateo_spinner.setAdapter(adapter);
        cateo_spinner.setOnItemSelectedListener(this);

        submitButton = findViewById(R.id.button_submit_income);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < emailArray.length; i++) {
                    RequestQueue requestQueue = Volley.newRequestQueue(SplitActivity.this);
                    String URL = "http://18.189.6.243/api/user/ValidateUserEmail"; // to be added
                    URL += "?email=" + emailArray[i];

                    final Map<String, String> params = new HashMap<String, String>();
                    params.put("email", emailArray[i]);

                    VolleyAPIService volleyAPIService = new VolleyAPIService();
                    volleyAPIService.callback = SplitActivity.this;
                    volleyAPIService.volleyPost(URL, params, SplitActivity.this);
                }
            }
        });

        ImageButton imageButton = findViewById(R.id.add_contact_button);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplitActivity.this , AddContact.class);
                startActivity(intent);
            }
        });

        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            contact_name =(String) b.get("contact_name");
            type_add = (String) b.get("type_add");
            emailList = (String) b.get("email_list");

            emailArray = emailList.split("\n");
            String[] nameArray = contact_name.split("\n");

            for(int i = 0; i < emailArray.length; i++) {
                DataVault.Split split = new DataVault.Split();
                split.name = nameArray[i];
                split.email = emailArray[i];
                splitArrayList.add(split);
            }
        }
        else {
            contact_name = "";
        }
        nameTextView.append(contact_name);
        split_accordingly_text.setText(type_add);
    }

    public void addSplitExpense(String userId) {
        String URL = "http://18.189.6.243/api/expense/AddExpense";
        DateHelper dateHelper = new DateHelper();
        String time = dateHelper.getGMTDate();
        String category = cateo_spinner.getSelectedItem().toString();

        double splitAmount = Double.parseDouble(amount.getText().toString()) / splitArrayList.size();

        for(int i = 0; i < splitArrayList.size(); i++) {
            splitArrayList.get(i).amount = String.valueOf(splitAmount);
        }

        Gson gson = new Gson();
        String splitJson = gson.toJson(splitArrayList.toArray());

        String description = "Food";
        String location = "Waterloo";
        String splitType = "equal";

        URL += "?user_id=" + userId +
                "&category_id=" + category +
                "&payment_method=Cash" +
                "&time=" + time +
                "&amount=" + amount.getText().toString() +
                "&split=" + splitJson +
                "&description=" + description +
                "&split_type=" + splitType +
                "&location=" + location;

        final Map<String, String> params = new HashMap<String, String>();
        params.put("user_id", userId);
        params.put("time", time);

        VolleyAPIService volleyAPIService = new VolleyAPIService();
        volleyAPIService.expenseCallback = SplitActivity.this;
        volleyAPIService.volleyPost(URL, params, SplitActivity.this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onExpenseSuccess(String result) {

    }

    @Override
    public void onSuccess(String result) {
        if (!result.equals("\"Invalid User\"")) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            DataVault.UserDetail userDetail = gson.fromJson(result, DataVault.UserDetail.class);

            String userid = String.valueOf(userDetail.getId());
            addSplitExpense(userid);
        }
    }
}
