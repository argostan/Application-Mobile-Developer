package com.example.FirmaAppSocial;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("status")
    private boolean status;

    @SerializedName("result")
    private LoginResultResponse result;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LoginResultResponse getResult() {
        return result;
    }

    public void setResult(LoginResultResponse result) {
        this.result = result;
    }
}
