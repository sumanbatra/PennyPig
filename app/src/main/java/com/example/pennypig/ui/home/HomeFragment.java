package com.example.pennypig.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.pennypig.Expense;
import com.example.pennypig.ExpenseCallback;
import com.example.pennypig.Income;
import com.example.pennypig.IncomeCallback;
import com.example.pennypig.Model.DataVault;
import com.example.pennypig.R;
import com.example.pennypig.SharedPreference.SaveSharedPreference;
import com.example.pennypig.Split;
import com.example.pennypig.VolleyAPIService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment implements IncomeCallback, ExpenseCallback {

    private HomeViewModel homeViewModel;

    protected static final String TAG = "MenuActivity";

    Button addExpenseButton;
    Button addIncomeButton;
    Button tempSplit;
    TextView incomeTextview;
    TextView expenseTextview;
    TextView totalAmountTextview;

    ProgressDialog progressDialog;

    double income;
    double expense;
    double total;
    boolean isIncomeReturned = false;
    boolean isExpenseReturned = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        addExpenseButton = (Button) root.findViewById(R.id.add_expense_button);
        addIncomeButton = (Button) root.findViewById(R.id.add_income_button);
        incomeTextview = (TextView) root.findViewById(R.id.income_textview);
        expenseTextview = (TextView) root.findViewById(R.id.expense_textview);
        totalAmountTextview = (TextView) root.findViewById(R.id.total_amount_textview);
        tempSplit = (Button) root.findViewById(R.id.temp_split);

        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Expense.class);
                startActivity(intent);
            }
        });

        addIncomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Income.class);
                startActivity(intent);
            }
        });

        tempSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Split.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        this.isIncomeReturned = false;
        this.isExpenseReturned = false;

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Wait while loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        getAllIncomes();
        getAllExpenses();
    }

    void getAllIncomes() {
        String URL = "http://18.189.6.243/api/income/GetUserIncomes";

        URL += "?user_id=" + SaveSharedPreference.getUserId(getActivity());

        final Map<String, String> params = new HashMap<String, String>();

        VolleyAPIService volleyAPIService = new VolleyAPIService();
        volleyAPIService.incomeCallback = HomeFragment.this;
        volleyAPIService.volleyPost(URL, params, getActivity());
    }

    void getAllExpenses() {
        String URL = "http://18.189.6.243/api/expense/GetUserExpenses";

        URL += "?user_id=" + SaveSharedPreference.getUserId(getActivity());

        final Map<String, String> params = new HashMap<String, String>();

        VolleyAPIService volleyAPIService = new VolleyAPIService();
        volleyAPIService.expenseCallback = HomeFragment.this;
        volleyAPIService.volleyPost(URL, params, getActivity());
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

}