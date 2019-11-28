package com.example.pennypig.Adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pennypig.Model.PassbookAdapterItem;
import com.example.pennypig.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class PassbookAdapter extends RecyclerView.Adapter<PassbookAdapter.ViewHolder> {
    private static final String TAG = "PassbookAdapter";

    private int listItemLayout;
    private ArrayList<PassbookAdapterItem> passbookAdapterItemArrayList;

    public PassbookAdapter(int layoutId, ArrayList<PassbookAdapterItem> passbookAdapterItemArrayList) {
        listItemLayout = layoutId;
        this.passbookAdapterItemArrayList = passbookAdapterItemArrayList;
    }

    @Override
    public int getItemCount() {
        return passbookAdapterItemArrayList == null ? 0 : passbookAdapterItemArrayList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        TextView amount = holder.amount;
        TextView timeStamp = holder.timeStamp;
        TextView category = holder.category;
        TextView bankOrCash = holder.bankORCash;

        PassbookAdapterItem p = passbookAdapterItemArrayList.get(listPosition);
        if(p.getColour() == "Green") {
            amount.setTextColor(Color.GREEN);
        }
        else {
            amount.setTextColor(Color.RED);
        }
        String amountString = "$ " + p.getAmount();
        amount.setText(amountString);
        timeStamp.setText(p.getTimeStamp());
        category.setText(p.getCategory());
        bankOrCash.setText(p.getBankOrCash());

        /* int i = 0;
        for (Map.Entry<Date,PassbookAdapterItem> entry : passbookAdapterItemMap.entrySet()) {
            if(i == listPosition) {
                PassbookAdapterItem p = passbookAdapterItemMap.get(entry.getKey());
                if(p.getColour() == "Green") {
                    amount.setTextColor(Color.GREEN);
                }
                else {
                    amount.setTextColor(Color.RED);
                }
                String amountString = "$ " + p.getAmount();
                amount.setText(amountString);
                break;
            }
            i++;
        } */
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView amount;
        public TextView timeStamp;
        public TextView category;
        public TextView bankORCash;

        public ViewHolder(View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.row_amount);
            timeStamp = itemView.findViewById(R.id.time_stamp);
            category = itemView.findViewById(R.id.recycler_category);
            bankORCash = itemView.findViewById(R.id.recycler_cash_or_bank);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.i(TAG, "onClick: ");
        }
    }
}