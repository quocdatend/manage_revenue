package com.hongoquocdat.manage_revenue.ui.intro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.hongoquocdat.manage_revenue.data.database.AppDatabase;
import com.hongoquocdat.manage_revenue.databinding.ActivityIntroBinding;
import com.hongoquocdat.manage_revenue.ui.main.MainActivity;
import com.hongoquocdat.manage_revenue.R;
import com.hongoquocdat.manage_revenue.ui.sign_in.SignInActivity;

import java.io.File;

public class IntroActivity extends AppCompatActivity {

    private ActivityIntroBinding binding;
    private IntroViewModel viewModel;
    private AppDatabase appDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addEvents();

        // check if connected fail to database

        appDatabase = AppDatabase.getInstance(this);
    }

    private void addEvents() {
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi tạo ViewModel
        viewModel = new ViewModelProvider(this).get(IntroViewModel.class);

        // Lắng nghe text từ ViewModel
        viewModel.getWelcomeText().observe(this, text -> {
            binding.txtWelcome.setText(text);
        });

        // Sự kiện nút Start
        binding.btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(IntroActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        });
    }

}
