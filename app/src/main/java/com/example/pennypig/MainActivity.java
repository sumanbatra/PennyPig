package com.example.pennypig;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private final String MAINACTIVITYTAG = "MAIN ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        /* try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e(MAINACTIVITYTAG, "Error in Thread.sleep");
        }*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
