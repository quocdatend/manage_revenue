package com.hongoquocdat.manage_revenue.ui.sign_up;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hongoquocdat.manage_revenue.R;
import com.hongoquocdat.manage_revenue.databinding.ActivitySignUpBinding;
import com.hongoquocdat.manage_revenue.ui.sign_in.SignInActivity;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private SignUpViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
    }

    private void addEvents() {

        TextView txtLogin = findViewById(R.id.txtLogin);
        txtLogin.setPaintFlags(txtLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
