package com.example.pennypig;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Split extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split);


        TextView textView = (TextView) findViewById(R.id.split);
        EditText editText = (EditText) findViewById(R.id.split_amount);
        Spinner cateo_spinner = (Spinner) findViewById(R.id.category_array);
        Spinner split_spinner = (Spinner) findViewById(R.id.split_accordingly);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.categories, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        cateo_spinner.setAdapter(adapter);
        cateo_spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.splitAccordingly, R.layout.support_simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        split_spinner.setAdapter(adapter1);
        split_spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }


}
