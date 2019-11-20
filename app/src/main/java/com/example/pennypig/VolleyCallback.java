package com.example.pennypig;

public interface VolleyCallback {
    void onSuccess(String result);
}

interface IncomeCallback {
    void onIncomeSuccess(String result);
}

interface ExpenseCallback {
    void onExpenseSuccess(String result);
}
