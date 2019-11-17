package com.example.pennypig;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ExpenseMaster extends AppCompatActivity {


    public void buttonClick(View view){
        String tag = view.getTag().toString();
        Toast.makeText(getApplicationContext(),tag,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_master);
    }
}
