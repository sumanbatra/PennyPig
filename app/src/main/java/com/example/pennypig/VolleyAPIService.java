package com.example.pennypig;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class VolleyAPIService extends Service {

    static final String VOLLEY_API_SERVICE = "VolleyAPIService";

    public VolleyCallback callback = null;
    public IncomeCallback incomeCallback = null;
    public ExpenseCallback expenseCallback = null;
    public SignUpCallback signUpCallback = null;

    public VolleyAPIService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void volleyPost(String URL, Map<String, String> param, Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final Map<String, String> params = param;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(callback != null) {
                            callback.onSuccess(response);
                        }
                        else if(incomeCallback != null) {
                            incomeCallback.onIncomeSuccess(response);
                        }
                        else if(expenseCallback != null) {
                            expenseCallback.onExpenseSuccess(response);
                        }
                        else if(signUpCallback != null) {
                            signUpCallback.onSignupSuccess(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY_API_SERVICE", String.valueOf(error));
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {;
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public void volleyPostWithoutParams(String URL, Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY_API_SERVICE", String.valueOf(error));
                    }
                });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public void volleyGet(String URL, Context context){
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY_API_SERVICE", String.valueOf(error));
            }
        });
        requestQueue.add(stringRequest);
    }
}

