package com.atguigu.androidandh5;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 *
 * 作用：java和js互调
 */
public class JavaAndJSActivity extends Activity implements View.OnClickListener {
    private EditText etNumber;
    private EditText etPassword;
    private Button btnLogin;
    /**
     * 加载网页或者说H5页面
     */
    private WebView webView;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-07-28 11:43:37 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        setContentView(R.layout.activity_java_and_js);
        etNumber = (EditText) findViewById(R.id.et_number);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2016-07-28 11:43:37 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            // Handle clicks for btnLogin
            login();
        }
    }

    private void login() {
        String numebr = etNumber.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(numebr) || TextUtils.isEmpty(password)) {
            Toast.makeText(JavaAndJSActivity.this, "账号或者密码为空", Toast.LENGTH_SHORT).show();
        } else {
            //登录
          // login(numebr);
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
    }


}
