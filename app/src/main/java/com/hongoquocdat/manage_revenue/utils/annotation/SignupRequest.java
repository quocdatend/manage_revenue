package com.hongoquocdat.manage_revenue.utils.annotation;

import androidx.annotation.NonNull;
import androidx.annotation.Size;

import org.intellij.lang.annotations.Pattern;

public class SignupRequest {

    @NonNull
    @Size(min = 3)
    public String name;

    @NonNull
    @Pattern("^[0-9]{9,11}$")
    public String phone;

    @NonNull
    @Size(min = 6)
    public String password;

    @NonNull
    public String confirmPassword;

    public boolean isPasswordMatch() {
        return password.equals(confirmPassword);
    }
}

