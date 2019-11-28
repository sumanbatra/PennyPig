package com.example.pennypig.ui.passbook;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pennypig.Adapters.PassbookAdapter;
import com.example.pennypig.ExpenseCallback;
import com.example.pennypig.Helpers.DateHelper;
import com.example.pennypig.IncomeCallback;
import com.example.pennypig.Model.DataVault;
import com.example.pennypig.Model.PassbookAdapterItem;
import com.example.pennypig.R;
import com.example.pennypig.SharedPreference.SaveSharedPreference;
import com.example.pennypig.VolleyAPIService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PassbookFragment extends Fragment implements IncomeCallback, ExpenseCallback {

    private PassbookViewModel passbookViewModel;
    private static final String TAG = "PassbookFragment";
    RecyclerView recyclerView;
    Map<Date, PassbookAdapterItem> passbookAdapterItemMap;
    ArrayList<PassbookAdapterItem> passbookAdapterItemArrayList;
    PassbookAdapter passbookAdapter;
    View rootView;

    ProgressDialog progressDialog;

    double income;
    double expense;
    double total;
    boolean isIncomeReturned = false;
    boolean isExpenseReturned = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        passbookViewModel = ViewModelProviders.of(this).get(PassbookViewModel.class);
        View root = inflater.inflate(R.layout.fragment_passbook, container, false);
        rootView = root;

        recyclerView = (RecyclerView) root.findViewById(R.id.passbook_recycler);
        passbookAdapterItemMap = new HashMap<Date, PassbookAdapterItem>();

        passbookAdapterItemArrayList = new ArrayList<PassbookAdapterItem>();

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
        volleyAPIService.incomeCallback = PassbookFragment.this;
        volleyAPIService.volleyPost(URL, params, getActivity());
    }

    void getAllExpenses() {
        String URL = "http://18.189.6.243/api/expense/GetUserExpenses";

        URL += "?user_id=" + SaveSharedPreference.getUserId(getActivity());

        final Map<String, String> params = new HashMap<String, String>();

        VolleyAPIService volleyAPIService = new VolleyAPIService();
        volleyAPIService.expenseCallback = PassbookFragment.this;
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
            Date incomeDate = DateHelper.GMTtoLocalTime(incomeDetail[i].time);
            PassbookAdapterItem passbookAdapterItem = new PassbookAdapterItem(incomeDetail[i].amount, incomeDetail[i].time, "Green", incomeDate, "Income", "Bank");
            passbookAdapterItemMap.put(incomeDate, passbookAdapterItem);
            passbookAdapterItemArrayList.add(passbookAdapterItem);
        }

        if(this.isExpenseReturned) {
            total = this.income - this.expense;
            Collections.sort(passbookAdapterItemArrayList);
            passbookAdapter = new PassbookAdapter(R.layout.passbook_list_row, passbookAdapterItemArrayList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addItemDecoration(new DividerItemDecoration(rootView.getContext(), LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(passbookAdapter);
        }

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
            Date expenseDate = DateHelper.GMTtoLocalTime(expenseDetails[i].time);
            PassbookAdapterItem passbookAdapterItem = new PassbookAdapterItem(expenseDetails[i].amount, expenseDetails[i].time, "Red", expenseDate, expenseDetails[i].category_id, expenseDetails[i].payment_method);
            passbookAdapterItemMap.put(expenseDate, passbookAdapterItem);
            passbookAdapterItemArrayList.add(passbookAdapterItem);
        }

        if(this.isIncomeReturned) {
            total = this.income - this.expense;
            Collections.sort(passbookAdapterItemArrayList);
            passbookAdapter = new PassbookAdapter(R.layout.passbook_list_row, passbookAdapterItemArrayList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addItemDecoration(new DividerItemDecoration(rootView.getContext(), LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(passbookAdapter);
        }

        Log.i(TAG, "onSuccess: ");
        this.isExpenseReturned = true;
    }
}