package com.hongoquocdat.manage_revenue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_intro);

            // Nút continue
            findViewById(R.id.btnStart).setOnClickListener(v -> {
                // Lưu trạng thái “đã xem intro”
//                getSharedPreferences("intro", MODE_PRIVATE)
//                        .edit()
//                        .putBoolean("done", true)
//                        .apply();

                startActivity(new Intent(IntroActivity.this, MainActivity.class));
                finish();
            });
        }

}
