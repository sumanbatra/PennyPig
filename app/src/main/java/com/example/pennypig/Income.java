
package com.example.pennypig;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class Income extends AppCompatActivity {

    private static final String TAG = "Income";
    TextView valueText;
    String Value = "";
    double initialValue=0;
    int equalCheck =0;

    public void setValue(View view){
        String tag = String.valueOf(view.getTag());
        if(tag.equals("buttonIncomeOne")){
            Log.i(TAG, "setValue: buttonOne");
            Value +="1";
        }
        else if(tag.equals("buttonIncomeTwo")){
            Log.i(TAG, "setValue: buttonTwo");
            Value +="2";
        }
        else if(tag.equals("buttonIncomeThree")){
            Log.i(TAG, "setValue: buttonThree");
            Value +="3";
        }
        else if(tag.equals("buttonIncomeFour")){
            Log.i(TAG, "setValue: buttonFour");
            Value +="4";
        }
        else if(tag.equals("buttonIncomeFive")){
            Log.i(TAG, "setValue: buttonFive");
            Value +="5";
        }
        else if(tag.equals("buttonIncomeSix")){
            Log.i(TAG, "setValue: buttonSix");
            Value +="6";
        }
        else if(tag.equals("buttonIncomeSeven")){
            Log.i(TAG, "setValue: buttonSeven");
            Value +="7";
        }
        else if(tag.equals("buttonIncomeEight")){
            Log.i(TAG, "setValue: buttonEight");
            Value +="8";
        }
        else if(tag.equals("buttonIncomeNine")){
            Log.i(TAG, "setValue: buttonNine");
            Value +="9";
        }
        else if(tag.equals("buttonIncomeZero")){
            Log.i(TAG, "setValue: buttonZero");
            Value +="0";
        }
        else if(tag.equals("buttonIncomeDot")){
            Log.i(TAG, "setValue: buttonDot");
            Value +=".";
        }
        else if(tag.equals("eraseButton") && Value.length()>0){
            Log.i(TAG, "setValue: Erase Button");
            Value = Value.substring(0,Value.length()-1);
            if (Value.equals("ERRO")){
                Value = "";
            }
        }
        else if(tag.equals("buttonIncomePlus")){
            initialValue = Double.parseDouble(Value);
            equalCheck = 1;
            Value = "";
        }
        else if(tag.equals("buttonIncomeMinus")){
            initialValue = Double.parseDouble(Value);
            equalCheck = 2;
            Value = "";
        }
        else if(tag.equals("buttonIncomeMultiplication")){
            initialValue = Double.parseDouble(Value);
            equalCheck = 3;
            Value = "";
        }
        else if(tag.equals("buttonIncomeDivision")){
            initialValue = Double.parseDouble(Value);
            equalCheck = 4;
            Value = "";
        }
        else if(tag.equals("buttonIncomeEquals")){
            if(equalCheck == 1) {
                initialValue += Double.parseDouble(Value);
                Value = String.valueOf(initialValue);
            }
            else if(equalCheck == 2) {
                initialValue -= Double.parseDouble(Value);
                Value = String.valueOf(initialValue);
            }
            else if(equalCheck == 3) {
                initialValue *= Double.parseDouble(Value);
                Value = String.valueOf(initialValue);
            }
            else if(equalCheck == 4) {
                if(Value.equals("0")){
                    Value = "ERROR";
                }
                else {
                    initialValue /= Double.parseDouble(Value);
                    Value = String.valueOf(initialValue);
                }
            }
            initialValue=0;
        }
        valueText.setText(Value);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        TextView textViewDate = findViewById(R.id.dateTextView);
        textViewDate.setText(currentDate);

        valueText = findViewById(R.id.valueText);

        ImageButton eraseButton = findViewById(R.id.eraseButton);
        eraseButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Value = "";
                valueText.setText(Value);
                return true;
            }
        });
    }
}
