package nvdhai12.androiddev.fashionfusion.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.textfield.TextInputEditText;

import nvdhai12.androiddev.fashionfusion.R;

public class RegisterActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private AppCompatButton btnRegister;
    private TextInputEditText edtEmail, edtPassword, edtFullName, edtConfirmPassword;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtEmail = findViewById(R.id.edt_reg_email);
        edtPassword = findViewById(R.id.edt_reg_password);
        edtFullName = findViewById(R.id.edt_reg_name);
        edtConfirmPassword = findViewById(R.id.edt_reg_confpass);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.yellow));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            int flags = decorView.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(flags);
        }

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Drawable drawable = getResources().getDrawable(R.drawable.baseline_arrow_back_ios_new_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(drawable);

        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performRestify();
            }
        });



        edtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // Nếu trường tên đăng nhập không còn focus (người dùng nhập xong và chuyển sang trường khác)
                if (!hasFocus) {
                    // Di chuyển trỏ nhập vào trường mật khẩu
                    edtFullName.requestFocus();
                }
            }
        });

        edtFullName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // Nếu trường tên đăng nhập không còn focus (người dùng nhập xong và chuyển sang trường khác)
                if (!hasFocus) {
                    // Di chuyển trỏ nhập vào trường mật khẩu
                    edtPassword.requestFocus();
                }
            }
        });

        edtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // Nếu trường tên đăng nhập không còn focus (người dùng nhập xong và chuyển sang trường khác)
                if (!hasFocus) {
                    // Di chuyển trỏ nhập vào trường mật khẩu
                    edtConfirmPassword.requestFocus();
                }
            }
        });


        edtConfirmPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Ẩn bàn phím ảo nếu bạn muốn
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtPassword.getWindowToken(), 0);
                    performRestify();
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.constraintlayout2).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Ẩn bàn phím ảo nếu đang hiển thị
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                // Hủy bỏ focus của trường đang có focus (nếu có)
                clearFocus();

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void performRestify() {
        String email = edtEmail.getText().toString();
        Intent intent = new Intent(RegisterActivity.this, VerificationCodeActivity.class);// Thay "code" bằng mã xác nhận bạn đã nhập
        intent.putExtra("user_email", email);
        startActivity(intent);
    }

    private void clearFocus() {
        edtEmail.clearFocus();
        edtFullName.clearFocus();
        edtPassword.clearFocus();
        edtConfirmPassword.clearFocus();
    }
}