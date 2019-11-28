package com.example.pennypig.Model;

import java.util.Date;

public class PassbookAdapterItem implements Comparable<PassbookAdapterItem>{
    private String amount;
    private String timeStamp;
    private String colour;
    private Date date;
    private String bankOrCash;
    private String category;

    public PassbookAdapterItem(String amount, String timeStamp, String colour, Date date, String category, String bankOrCash) {
        this.amount = amount;
        this.colour = colour;
        this.date = date;
        this.timeStamp = timeStamp;
        this.category = category;
        this.bankOrCash = bankOrCash;
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

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBankOrCash() {
        return bankOrCash;
    }

    public void setBankOrCash(String bankOrCash) {
        this.bankOrCash = bankOrCash;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        category = category;
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
