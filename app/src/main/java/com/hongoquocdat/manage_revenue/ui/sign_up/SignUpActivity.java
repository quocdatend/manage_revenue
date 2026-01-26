package com.hongoquocdat.manage_revenue.ui.sign_up;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.hongoquocdat.manage_revenue.R;
import com.hongoquocdat.manage_revenue.data.database.AppDatabase;
import com.hongoquocdat.manage_revenue.data.entity.Role;
import com.hongoquocdat.manage_revenue.data.entity.User;
import com.hongoquocdat.manage_revenue.databinding.ActivitySignUpBinding;
import com.hongoquocdat.manage_revenue.repository.UserRepository;
import com.hongoquocdat.manage_revenue.ui.sign_in.SignInActivity;
import com.hongoquocdat.manage_revenue.utils.PasswordHashHandler;
import com.hongoquocdat.manage_revenue.utils.annotation.SignupRequest;

import java.util.logging.Logger;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private SignUpViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        AppDatabase db = AppDatabase.getInstance(this);
//        User user = User();
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

        binding.btnRegister.setOnClickListener(v -> {
            EditText edtName = findViewById(R.id.editTextName);
            EditText edtPhone = findViewById(R.id.editTextPhone);
            EditText edtPass = findViewById(R.id.editTextPassword);
            EditText edtConfirmPass = findViewById(R.id.editTextConfirmPassword);
            SignupRequest req = new SignupRequest();
            req.name = edtName.getText().toString().trim();
            req.phone = edtPhone.getText().toString().trim();
            req.password = edtPass.getText().toString().trim();
            req.confirmPassword = edtConfirmPass.getText().toString().trim();

            String passwordPattern =
                    "^" +
                            "(?=.*[0-9])" +         // at least 1 digit
                            "(?=.*[a-z])" +         // at least 1 lowercase letter
                            "(?=.*[A-Z])" +         // at least 1 uppercase letter
                            "(?=.*[@#$%^&+=])" +    // at least 1 special character
                            "(?=\\S+$)" +           // no spaces
                            ".{8,}" +               // at least 8 characters
                            "$";

            // Validate
            if (req.name.length() < 6) {
                edtName.setError("Name must be at least 6 characters");
                return;
            }

            if (!req.phone.matches("^[0-9]{9,11}$")) {
                edtPhone.setError("Phone must be 9–11 digits");
                return;
            }

            if (req.password.length() < 6) {
                edtPass.setError("Password must be at least 6 characters");
                return;
            }

            if (!req.password.matches(passwordPattern)) {
                edtPass.setError("Password must contain upper, lower, number, special char and 8+ chars");
                return;
            }

            if (!req.isPasswordMatch()) {
                edtConfirmPass.setError("Passwords do not match");
                return;
            }

//            AppDatabase appDatabase = AppDatabase.getInstance(this);
            UserRepository userRepository = new UserRepository(this.getApplication());
            PasswordHashHandler passwordHashHandler = new PasswordHashHandler();
            PasswordHashHandler.HashedPassword hashedPassword = passwordHashHandler.hashPasswordWithSalt(edtPass.getText().toString());
            User user = new User();
            user.setName(edtName.getText().toString());
            user.setPhone(edtPhone.getText().toString());
            user.setPass(hashedPassword.getSalt() + "+" + hashedPassword.getHash());
            user.setRole(Role.USER);
            userRepository.insert(user);

            // check if add sql error

            Toast.makeText(this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();

            // move to sign in
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        });

    }
}
