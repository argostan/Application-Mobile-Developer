package com.example.FirmaAppSocial;

import com.google.gson.annotations.SerializedName;

public class ContattoResponse {


    @SerializedName("status")
    private boolean status;

    @SerializedName("result")
    private String result;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
}
