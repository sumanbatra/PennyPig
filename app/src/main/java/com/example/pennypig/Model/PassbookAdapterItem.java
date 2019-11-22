package com.example.pennypig.Model;

import java.util.Date;

public class PassbookAdapterItem implements Comparable<PassbookAdapterItem>{
    private String amount;
    private String colour;
    private Date date;

    public PassbookAdapterItem(String amount, String colour, Date date) {
        this.amount = amount;
        this.colour = colour;
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }


    @Override
    public int compareTo(PassbookAdapterItem o) {
        if(date.compareTo(o.date) == 0) {
            return 0;
        }
        else if(date.compareTo(o.date) < 0) {
            return 1;
        }
        else {
            return -1;
        }
    }
}
