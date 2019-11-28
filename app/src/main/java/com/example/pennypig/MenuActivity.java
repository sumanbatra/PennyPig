package com.example.pennypig;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pennypig.Model.DataVault;
import com.example.pennypig.SharedPreference.SaveSharedPreference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity implements IncomeCallback, ExpenseCallback{

    protected static final String TAG = "MenuActivity";

    Button addExpenseButton;
    Button addIncomeButton;
    TextView incomeTextview;
    TextView expenseTextview;
    TextView totalAmountTextview;
    int intent_category_number;
    ImageButton medicine,travel,entertainment,rent,grocery,bills;

    ProgressDialog progressDialog;

    double income;
    double expense;
    double total;
    boolean isIncomeReturned = false;
    boolean isExpenseReturned = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        addExpenseButton = (Button) findViewById(R.id.add_expense_button);
        addIncomeButton = (Button) findViewById(R.id.add_income_button);
        incomeTextview = (TextView) findViewById(R.id.income_textview);
        expenseTextview = (TextView) findViewById(R.id.expense_textview);
        totalAmountTextview = (TextView) findViewById(R.id.total_amount_textview);

        medicine = findViewById(R.id.ExpenseMedicine);
        grocery = findViewById(R.id.ExpenseGrocery);
        bills = findViewById(R.id.ExpenseBills);
        travel = findViewById(R.id.ExpenseTravel);
        rent = findViewById(R.id.ExpenseRent);
        entertainment = findViewById(R.id.ExpenseEntertainment);

        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent_category_number =0;
                Intent intent = new Intent(MenuActivity.this,ExpenseActivity.class);
                intent.putExtra("intent_category_number", intent_category_number);
                startActivity(intent);
            }
        });

        grocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent_category_number =1;
                Intent intent = new Intent(MenuActivity.this,ExpenseActivity.class);
                intent.putExtra("intent_category_number", intent_category_number);
                startActivity(intent);
            }
        });

        bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent_category_number =2;
                Intent intent = new Intent(MenuActivity.this,ExpenseActivity.class);
                intent.putExtra("intent_category_number", intent_category_number);
                startActivity(intent);
            }
        });

        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent_category_number =3;
                Intent intent = new Intent(MenuActivity.this,ExpenseActivity.class);
                intent.putExtra("intent_category_number", intent_category_number);
                startActivity(intent);
            }
        });

        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent_category_number =4;
                Intent intent = new Intent(MenuActivity.this,ExpenseActivity.class);
                intent.putExtra("intent_category_number", intent_category_number);
                startActivity(intent);
            }
        });

        entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent_category_number =5;
                Intent intent = new Intent(MenuActivity.this,ExpenseActivity.class);
                intent.putExtra("intent_category_number", intent_category_number);
                startActivity(intent);
            }
        });

        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ExpenseActivity.class);
                startActivity(intent);
            }
        });

        addIncomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, IncomeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        this.isIncomeReturned = false;
        this.isExpenseReturned = false;

        progressDialog = new ProgressDialog(MenuActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Wait while loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        getAllIncomes();
        getAllExpenses();
    }

    void getAllIncomes() {
        String URL = "http://18.189.6.243/api/income/GetUserIncomes";

        URL += "?user_id=" + SaveSharedPreference.getUserId(MenuActivity.this);

        final Map<String, String> params = new HashMap<String, String>();

        VolleyAPIService volleyAPIService = new VolleyAPIService();
        volleyAPIService.incomeCallback = MenuActivity.this;
        volleyAPIService.volleyPost(URL, params, MenuActivity.this);
    }

    void getAllExpenses() {
        String URL = "http://18.189.6.243/api/expense/GetUserExpenses";

        URL += "?user_id=" + SaveSharedPreference.getUserId(MenuActivity.this);

        final Map<String, String> params = new HashMap<String, String>();

        VolleyAPIService volleyAPIService = new VolleyAPIService();
        volleyAPIService.expenseCallback = MenuActivity.this;
        volleyAPIService.volleyPost(URL, params, MenuActivity.this);
    }

    @Override
    public void onIncomeSuccess(String result) {
        progressDialog.dismiss();

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        DataVault.IncomeDetail[] incomeDetail = gson.fromJson(result, DataVault.IncomeDetail[].class);

        this.income = 0;

        for (int i = 0; i < incomeDetail.length; i++) {
            income += Double.parseDouble(incomeDetail[i].amount);
        }

        if(this.isExpenseReturned) {
            total = this.income - this.expense;
            if(total >= 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    totalAmountTextview.setTextColor(getResources().getColor(R.color.holo_green_dark, null));
                }
            }
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    totalAmountTextview.setTextColor(getResources().getColor(R.color.holo_red_dark, null));
                }
            }
            totalAmountTextview.setText("$ " + String.valueOf(total));
        }

        incomeTextview.setText("$ " + String.valueOf(income));
        Log.i(TAG, "onSuccess: ");
        this.isIncomeReturned = true;
    }

    @Override
    public void onExpenseSuccess(String result) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        DataVault.ExpenseDetails[] expenseDetails = gson.fromJson(result, DataVault.ExpenseDetails[].class);

        this.expense = 0;

        for (int i = 0; i < expenseDetails.length; i++) {
            expense += Double.parseDouble(expenseDetails[i].amount);
        }

        if(this.isIncomeReturned) {
            total = this.income - this.expense;
            if(total >= 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    totalAmountTextview.setTextColor(getResources().getColor(R.color.holo_green_dark, null));
                }
            }
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    totalAmountTextview.setTextColor(getResources().getColor(R.color.holo_red_dark, null));
                }
            }
            totalAmountTextview.setText("$ " + String.valueOf(total));
        }

        expenseTextview.setText("$ " + String.valueOf(expense));
        Log.i(TAG, "onSuccess: ");
        this.isExpenseReturned = true;
    }

    public void setCategory(View view){
        String tag = String.valueOf(view.getTag());
        if(tag.equals("ExpenseMedicine")){
            Log.i(TAG, "setValue: buttonOne");
            intent_category_number =0;
        }
        else if(tag.equals("ExpenseTravel")){
            Log.i(TAG, "setValue: buttonTwo");
            intent_category_number = 1;
        }
        else if(tag.equals("ExpenseEntertainment")){
            Log.i(TAG, "setValue: buttonThree");
            intent_category_number =2;
        }
        else if(tag.equals("ExpenseBills")){
            Log.i(TAG, "setValue: buttonFour");
            intent_category_number =3;
        }
        else if(tag.equals("ExpenseRent")){
            Log.i(TAG, "setValue: buttonFive");
            intent_category_number =4;
        }
        else if(tag.equals("ExpenseGrocery")){
            Log.i(TAG, "setValue: buttonSix");
            intent_category_number =5;
        }
        Intent intent = new Intent(MenuActivity.this,ExpenseActivity.class);
        intent.putExtra("intent_category_number", intent_category_number);
        startActivity(intent);
    }
}
