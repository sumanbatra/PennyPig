package com.example.pennypig;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.pennypig.Helpers.DateHelper;
import com.example.pennypig.Model.DataVault;
import com.example.pennypig.SharedPreference.SaveSharedPreference;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ExpenseActivity extends AppCompatActivity implements ExpenseCallback{

    private static final String TAG = "ExpenseActivity";

    TextView valueText;
    String value = "";
    double initialValue=0;
    int equalCheck =0;
    ProgressDialog progressDialog;
    Button buttonExpenseFinal;
    RadioGroup radioGroup;
    String paymentMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        buttonExpenseFinal = (Button) findViewById(R.id.buttonExpenseFinal);
        valueText = findViewById(R.id.valueText);
        radioGroup = findViewById(R.id.radio_group);

        radioGroup.clearCheck();

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        TextView textViewDate = findViewById(R.id.dateTextView);
        textViewDate.setText(currentDate);

        progressDialog = new ProgressDialog(ExpenseActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Wait while loading...");
        progressDialog.setCancelable(false);

        ImageButton eraseButton = findViewById(R.id.eraseExpenseButton);
        eraseButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                value = "";
                valueText.setText(value);
                return true;
            }
        });

        buttonExpenseFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                String URL = "http://18.189.6.243/api/expense/AddExpense";
                String userId = SaveSharedPreference.getUserId(ExpenseActivity.this);
                DateHelper dateHelper = new DateHelper();
                String time = dateHelper.getGMTDate();

                if(paymentMethod == null) {
                    paymentMethod = "Cash";
                }

                ArrayList<DataVault.Split> splitArrayList = new ArrayList<DataVault.Split>();
                DataVault.Split split = new DataVault.Split();
                split.name = SaveSharedPreference.getUserName(ExpenseActivity.this);
                split.email = SaveSharedPreference.getUserEmail(ExpenseActivity.this);
                split.amount = value;

                splitArrayList.add(split);

                Gson gson = new Gson();
                String splitJson = gson.toJson(splitArrayList.toArray());

                String description = "Food";
                String location = "Waterloo";
                String splitType = "unequal";

                URL += "?user_id=" + userId +
                        "&category_id=" + "Others" +
                        "&payment_method=" + paymentMethod +
                        "&time=" + time +
                        "&amount=" + value +
                        "&split=" + splitJson +
                        "&description=" + description +
                        "&split_type=" + splitType +
                        "&location=" + location;

                final Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userId);
                params.put("time", time);
                params.put("amount", value);

                VolleyAPIService volleyAPIService = new VolleyAPIService();
                volleyAPIService.expenseCallback = ExpenseActivity.this;
                volleyAPIService.volleyPost(URL, params, ExpenseActivity.this);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checkedButton = checkedId;
                RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
                paymentMethod = String.valueOf(radioButton.getText());
                Log.i(TAG, "onCheckedChanged: ");
            }
        });
    }

    public void setValue(View view){
        String tag = String.valueOf(view.getTag());
        if(tag.equals("buttonExpenseOne")){
            Log.i(TAG, "setValue: buttonOne");
            value +="1";
        }
        else if(tag.equals("buttonExpenseTwo")){
            Log.i(TAG, "setValue: buttonTwo");
            value +="2";
        }
        else if(tag.equals("buttonExpenseThree")){
            Log.i(TAG, "setValue: buttonThree");
            value +="3";
        }
        else if(tag.equals("buttonExpenseFour")){
            Log.i(TAG, "setValue: buttonFour");
            value +="4";
        }
        else if(tag.equals("buttonExpenseFive")){
            Log.i(TAG, "setValue: buttonFive");
            value +="5";
        }
        else if(tag.equals("buttonExpenseSix")){
            Log.i(TAG, "setValue: buttonSix");
            value +="6";
        }
        else if(tag.equals("buttonExpenseSeven")){
            Log.i(TAG, "setValue: buttonSeven");
            value +="7";
        }
        else if(tag.equals("buttonExpenseEight")){
            Log.i(TAG, "setValue: buttonEight");
            value +="8";
        }
        else if(tag.equals("buttonExpenseNine")){
            Log.i(TAG, "setValue: buttonNine");
            value +="9";
        }
        else if(tag.equals("buttonExpenseZero")){
            Log.i(TAG, "setValue: buttonZero");
            value +="0";
        }
        else if(tag.equals("buttonExpenseDot")){
            Log.i(TAG, "setValue: buttonDot");
            value +=".";
        }
        else if(tag.equals("eraseButton") && value.length()>0){
            Log.i(TAG, "setValue: Erase Button");
            value = value.substring(0, value.length()-1);
            if (value.equals("ERRO")){
                value = "";
            }
        }
        else if(tag.equals("buttonExpensePlus")){
            initialValue = Double.parseDouble(value);
            equalCheck = 1;
            value = "";
        }
        else if(tag.equals("buttonExpenseMinus")){
            initialValue = Double.parseDouble(value);
            equalCheck = 2;
            value = "";
        }
        else if(tag.equals("buttonExpenseMultiplication")){
            initialValue = Double.parseDouble(value);
            equalCheck = 3;
            value = "";
        }
        else if(tag.equals("buttonExpenseDivision")){
            initialValue = Double.parseDouble(value);
            equalCheck = 4;
            value = "";
        }
        else if(tag.equals("buttonExpenseEquals")){
            if(equalCheck == 1) {
                initialValue += Double.parseDouble(value);
                value = String.valueOf(initialValue);
            }
            else if(equalCheck == 2) {
                initialValue -= Double.parseDouble(value);
                value = String.valueOf(initialValue);
            }
            else if(equalCheck == 3) {
                initialValue *= Double.parseDouble(value);
                value = String.valueOf(initialValue);
            }
            else if(equalCheck == 4) {
                if(value.equals("0")){
                    value = "ERROR";
                }
                else {
                    initialValue /= Double.parseDouble(value);
                    value = String.valueOf(initialValue);
                }
            }
            initialValue=0;
        }
        valueText.setText(value);

    }

    @Override
    public void onExpenseSuccess(String result) {
        Log.i(TAG, "onSuccess: " + result);

        valueText.setText("");

        progressDialog.dismiss();
    }
}
