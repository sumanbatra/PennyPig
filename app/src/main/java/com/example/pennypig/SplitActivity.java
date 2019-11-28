package com.example.pennypig;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pennypig.Helpers.DateHelper;
import com.example.pennypig.Model.DataVault;
import com.example.pennypig.SharedPreference.SaveSharedPreference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SplitActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, ExpenseCallback {

    String contact_name,final_contact_name = "";
    String type_add;
    String emailList;
    ArrayList<DataVault.Split> splitArrayList;
    Button submitButton;
    EditText amount;
    Spinner cateo_spinner;

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
                String URL = "http://18.189.6.243/api/expense/AddExpense";
                String userId = SaveSharedPreference.getUserId(SplitActivity.this);
                DateHelper dateHelper = new DateHelper();
                String time = dateHelper.getGMTDate();
                String category = cateo_spinner.getSelectedItem().toString();

                ArrayList<DataVault.Split> splitArrayList = new ArrayList<DataVault.Split>();
                DataVault.Split split = new DataVault.Split();
                split.name = SaveSharedPreference.getUserName(SplitActivity.this);
                split.email = SaveSharedPreference.getUserEmail(SplitActivity.this);

                splitArrayList.add(split);

                Gson gson = new Gson();
                String splitJson = gson.toJson(splitArrayList.toArray());

                String description = "Food";
                String location = "Waterloo";
                String splitType = "unequal";

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
        });

        ImageButton imageButton = findViewById(R.id.add_contact_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplitActivity.this, AddContact.class);
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

            String[] emailArray = emailList.split("\n");
            String[] nameArray = contact_name.split("\n");

            for(int i = 0; i < emailArray.length; i++) {
                DataVault.Split split = new DataVault.Split();
                split.name = nameArray[i];
                split.email = emailArray[i];
            }
        }
        else {
            contact_name = "";
        }
//        final_contact_name += "\n"+contact_name;
        nameTextView.append(contact_name);
//        nameTextView.setText(final_contact_name);
        split_accordingly_text.setText(type_add);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
//        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onExpenseSuccess(String result) {

    }
}
