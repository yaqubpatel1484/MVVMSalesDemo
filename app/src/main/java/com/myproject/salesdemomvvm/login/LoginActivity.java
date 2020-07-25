package com.myproject.salesdemomvvm.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.myproject.salesdemomvvm.R;
import com.myproject.salesdemomvvm.activities.BaseActivity;
import com.myproject.salesdemomvvm.utils.Methods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    private EditText mEdtUserId, mEdtPassword;
    private LoginViewModel viewModel;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        initView();

    }

    private void initView() {

        Context mContext = this;

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.mLoginListener = this;
        mProgressDialog = new ProgressDialog(mContext);

        mEdtUserId = findViewById(R.id.login_activity_edtUserId);
        mEdtPassword = findViewById(R.id.login_activity_edtPassword);

        Button mBtnLogin = findViewById(R.id.login_btnLogin);
        mBtnLogin.setOnClickListener(new LoginBtnClickListener());

    }

    private class LoginBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

                viewModel.sendData(LoginActivity.this,
                        mEdtUserId.getText().toString(),
                        mEdtPassword.getText().toString());

        }
    }

    @Override
    public void onStarted() {
        mProgressDialog.create();
    }

    @Override
    public void onSuccess(String message) {

        Methods.toast(message);
        mProgressDialog.dismiss();
        startActivity(new Intent(LoginActivity.this, BaseActivity.class));


    }

    @Override
    public void onFailure(String message) {
        mProgressDialog.dismiss();
        Methods.toast(message);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
