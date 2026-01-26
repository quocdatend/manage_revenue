package com.hongoquocdat.manage_revenue.ui.sign_in;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.hongoquocdat.manage_revenue.R;
import com.hongoquocdat.manage_revenue.data.entity.User;
import com.hongoquocdat.manage_revenue.databinding.ActivityIntroBinding;
import com.hongoquocdat.manage_revenue.databinding.ActivitySignInBinding;
import com.hongoquocdat.manage_revenue.repository.UserRepository;
import com.hongoquocdat.manage_revenue.ui.intro.IntroActivity;
import com.hongoquocdat.manage_revenue.ui.intro.IntroViewModel;
import com.hongoquocdat.manage_revenue.ui.main.MainActivity;
import com.hongoquocdat.manage_revenue.ui.sign_up.SignUpActivity;

public class SignInActivity extends AppCompatActivity{
    private ActivitySignInBinding binding;
    private SignInViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
    }

    private void addEvents() {
        setupRegisterLink();

        login();

    }

    private void setupRegisterLink() {
        TextView textView = findViewById(R.id.txtRegister);
        SpannableString spannableString = new SpannableString("Don't have an account? Register");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Xử lý sự kiện khi người dùng nhấn vào "Register"
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        };
        // Đặt vị trí của từ "Register" trong chuỗi
        int startIndex = spannableString.toString().indexOf("Register");
        int endIndex = startIndex + "Register".length();
        // Áp dụng ClickableSpan cho từ "Register"
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // Tùy chọn: Thêm màu và gạch chân cho link
        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new UnderlineSpan(), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // Đặt text cho TextView
        textView.setText(spannableString);
        // Đảm bảo TextView có thể nhận sự kiện click
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        // Xử lý sự kiện khi người dùng nhấn nút đăng ký
    }

    private void login() {
        binding.btnSignIn.setOnClickListener(v -> {
            String name = binding.editTextName.getText().toString().trim();
            String pass = binding.editTextPassword.getText().toString().trim();
            if (name.isEmpty() || pass.isEmpty()){
                binding.editTextName.setError("Please enter your name");
                binding.editTextPassword.setError("Please enter your password");
            } else {
                // check user
                UserRepository userRepository = new UserRepository(this.getApplication());
                User user = userRepository.getUser(name, pass);
                if (user == null) {
                    binding.editTextName.setError("Invalid name or password");
                    binding.editTextPassword.setError("Invalid name or password");
                } else{
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
