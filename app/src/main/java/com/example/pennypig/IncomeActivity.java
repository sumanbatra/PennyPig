
package com.example.pennypig;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pennypig.Helpers.DateHelper;
import com.example.pennypig.SharedPreference.SaveSharedPreference;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class IncomeActivity extends AppCompatActivity implements IncomeCallback {

    private static final String TAG = "IncomeActivity";
    TextView valueText;
    Button buttonIncomeFinal;
    String value = "";
    double initialValue = 0;
    int equalCheck = 0;
    ProgressDialog progressDialog;
    boolean checkDot = false;
    boolean checkNumber = false;
    int maxtwo = 0;
    boolean dotpressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        progressDialog = new ProgressDialog(IncomeActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Wait while loading...");
        progressDialog.setCancelable(false);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        TextView textViewDate = findViewById(R.id.dateTextView);
        textViewDate.setText(currentDate);
        buttonIncomeFinal = (Button) findViewById(R.id.buttonIncomeFinal);

        valueText = findViewById(R.id.valueText);

        ImageButton eraseIncomeButton = findViewById(R.id.eraseIncomeButton);
        eraseIncomeButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                value = "";
                valueText.setText(value);
                return true;
            }
        });

        buttonIncomeFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(IncomeActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
                builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);


                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addIncomeToDB();
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    public void addIncomeToDB() {
        progressDialog.show();

        String URL = "http://18.189.6.243/api/income/AddIncome";
        String userId = SaveSharedPreference.getUserId(IncomeActivity.this);
        DateHelper dateHelper = new DateHelper();
        String time = dateHelper.getGMTDate();

        URL += "?user_id=" + userId + "&time=" + time + "&amount=" + value;

        final Map<String, String> params = new HashMap<String, String>();
        params.put("user_id", userId);
        params.put("time", time);
        params.put("amount", value);

        VolleyAPIService volleyAPIService = new VolleyAPIService();
        volleyAPIService.incomeCallback = IncomeActivity.this;
        volleyAPIService.volleyPost(URL, params, IncomeActivity.this);
    }


    public void number(String tag){
        if (tag.equals("buttonIncomeOne")) {
            Log.i(TAG, "setValue: buttonOne");
            value += "1";
            checkNumber = true;
        } else if (tag.equals("buttonIncomeTwo")) {
            Log.i(TAG, "setValue: buttonTwo");
            value += "2";
            checkNumber = true;
        } else if (tag.equals("buttonIncomeThree")) {
            Log.i(TAG, "setValue: buttonThree");
            value += "3";
            checkNumber = true;
        } else if (tag.equals("buttonIncomeFour")) {
            Log.i(TAG, "setValue: buttonFour");
            value += "4";
            checkNumber = true;
        } else if (tag.equals("buttonIncomeFive")) {
            Log.i(TAG, "setValue: buttonFive");
            value += "5";
            checkNumber = true;
        } else if (tag.equals("buttonIncomeSix")) {
            Log.i(TAG, "setValue: buttonSix");
            value += "6";
            checkNumber = true;
        } else if (tag.equals("buttonIncomeSeven")) {
            Log.i(TAG, "setValue: buttonSeven");
            value += "7";
            checkNumber = true;
        } else if (tag.equals("buttonIncomeEight")) {
            Log.i(TAG, "setValue: buttonEight");
            value += "8";
            checkNumber = true;
        } else if (tag.equals("buttonIncomeNine")) {
            Log.i(TAG, "setValue: buttonNine");
            value += "9";
            checkNumber = true;

        } else if (tag.equals("buttonIncomeZero") && value.length() > 0) {
            Log.i(TAG, "setValue: buttonZero");
            value += "0";
            checkNumber = true;
        }
    }

    public void setValue(View view) {
        try {
            String tag = String.valueOf(view.getTag());

            if (value.equals("ERROR")) {
                value = "";
                valueText.setText(value);
            }
            if (dotpressed == false) {
                number(tag);
            } else if (dotpressed && maxtwo <= 1) {
                number(tag);
                maxtwo++;
            }
            if (tag.equals("buttonIncomeDot") && checkDot == false) {
                Log.i(TAG, "setValue: buttonDot");
                value += ".";
                checkNumber = true;
                dotpressed = true;
                checkDot = true;
            } else if (tag.equals("eraseIncomeButton") && value.length() > 0) {
                Log.i(TAG, "setValue: Erase Button");
                String delnum = value.substring(value.length() - 1, value.length());
                value = value.substring(0, value.length() - 1);
                if (value.contains(".")) {
                    checkDot = true;
                } else {
                    checkDot = false;
                }
                if (value.equals("ERRO")) {
                    value = "";
                }
                if (dotpressed && maxtwo > 0) {
                    maxtwo--;
                } else if (dotpressed && maxtwo < 0) {
                    dotpressed = false;
                }
                if (delnum.equals(".")) {
                    dotpressed = false;
                    maxtwo = 0;
                }
                Log.i(TAG, "delValue: " + delnum);
                Log.i(TAG, "delValue: " + maxtwo);

            } else if (tag.equals("buttonIncomePlus") && checkNumber) {
                initialValue = Double.parseDouble(value);
                equalCheck = 1;
                checkNumber = false;
                dotpressed = false;
                value = "";
            } else if (tag.equals("buttonIncomeMinus") && checkNumber) {
                initialValue = Double.parseDouble(value);
                equalCheck = 2;
                checkNumber = false;
                dotpressed = false;
                value = "";
            } else if (tag.equals("buttonIncomeMultiplication") && checkNumber) {
                initialValue = Double.parseDouble(value);
                equalCheck = 3;
                checkNumber = false;
                dotpressed = false;
                value = "";
            } else if (tag.equals("buttonIncomeDivision") && checkNumber) {
                initialValue = Double.parseDouble(value);
                equalCheck = 4;
                checkNumber = false;
                dotpressed = false;
                value = "";
            } else if (tag.equals("buttonIncomeEquals") && checkNumber) {
                if (equalCheck == 1) {
                    initialValue += Double.parseDouble(value);
                    value = String.valueOf(initialValue);
                } else if (equalCheck == 2) {
                    initialValue -= Double.parseDouble(value);
                    value = String.valueOf(initialValue);
                } else if (equalCheck == 3) {
                    initialValue *= Double.parseDouble(value);
                    value = String.valueOf(initialValue);
                } else if (equalCheck == 4) {
                    if (value.equals("0")) {
                        value = "ERROR";
                    } else {
                        initialValue /= Double.parseDouble(value);
                        value = String.valueOf(initialValue);
                    }
                }
                initialValue = 0;
                checkNumber = false;
            }
            valueText.setText(value);
        }
        catch (Exception e) {
            // Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            value = "";
            valueText.setText("");

        }
    }

    @Override
    public void onIncomeSuccess(String result) {
        Log.i(TAG, "onSuccess: " + result);

        valueText.setText("");

        progressDialog.dismiss();
    }
}
