package com.example.smartsunshade;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button login;
    public static final int LOGING = 1;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPass;
    //用户名
    private final static String u = "123456";
    private final static String p = "123456";

    private final static String remember_password = "remember_password";
    private final static String accountStr = "account";
    private final static String passwordStr = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        rememberPass = findViewById(R.id.remember_pass);
        boolean isRemember = pref.getBoolean(remember_password, false);
        imageView = findViewById(R.id.image_view);
        final EditText usernameEdit = findViewById(R.id.userName);
        final EditText passwordEdit = findViewById(R.id.passWord);
        if (isRemember) {
            String account = pref.getString(accountStr, "");
            String password = pref.getString(passwordStr, "");
            usernameEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
            if (u.equals(account) && p.equals(password)) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }

        login = findViewById(R.id.logIn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid = usernameEdit.getText().toString();
                String passw = passwordEdit.getText().toString();

                String pattern = "^[a-zA-Z0-9_]{4,16}$";
                if (!Pattern.matches(pattern, userid)) {
                    Toast.makeText(getApplicationContext(), "用户名非法！只能包含大小写字母数字下划线！", Toast.LENGTH_SHORT).show();
                } else if (u.equals(userid) && p.equals(passw)) {
                    editor = pref.edit();
                    if (rememberPass.isChecked()) {
                        editor.putBoolean(remember_password, true);
                        editor.putString(accountStr, userid);
                        editor.putString(passwordStr, passw);
                    } else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "用户名或密码错误！", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}
