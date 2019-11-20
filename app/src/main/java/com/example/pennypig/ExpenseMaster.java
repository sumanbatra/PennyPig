package com.example.pennypig;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        Button ibutton = findViewById(R.id.buttonIncomePage);
        ibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpenseMaster.this,Income.class);
                startActivity(intent);
            }
        });

        Button ebutton = findViewById(R.id.buttonExpensePage);
        ebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpenseMaster.this,Expense.class);
                startActivity(intent);
            }
        });
    }
}
