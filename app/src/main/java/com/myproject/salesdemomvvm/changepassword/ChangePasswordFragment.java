package com.myproject.salesdemomvvm.changepassword;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.myproject.salesdemomvvm.R;
import com.myproject.salesdemomvvm.application.MyApplication;
import com.myproject.salesdemomvvm.utils.Methods;
import com.myproject.salesdemomvvm.utils.SessionManager;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class ChangePasswordFragment extends Fragment implements ChangePasswordListener {

    private ChangePasswordViewModel mViewModel;
    private EditText mEdtOldPassword, mEdtNewPassword, mEdtRetypeNewPassword;
    private SessionManager mSessionManager;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.change_password_fragment, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View rootView) {

        mSessionManager = new SessionManager();
        mViewModel = new ViewModelProvider(this).get(ChangePasswordViewModel.class);
        mViewModel.changePasswordListener = this;

        mEdtOldPassword = rootView.findViewById(R.id.executive_change_password_edtOldPassword);
        mEdtNewPassword = rootView.findViewById(R.id.executive_change_password_edtNewPassword);
        mEdtRetypeNewPassword = rootView.findViewById(R.id.executive_change_password_edtRetypeNewPassword);

        Button mBtnUpdate = rootView.findViewById(R.id.executive_change_password_btnUpdate);
        mBtnUpdate.setOnClickListener(new BtnSubmitClickListener());
    }

    private class BtnSubmitClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            HashMap<String, String> user = mSessionManager.getExecutiveDetails();

            String username = user.get(mSessionManager.KEY_USERNAME);
            String password = user.get(mSessionManager.KEY_PASSWORD);

            String oldPassword = mEdtOldPassword.getText().toString();
            String passwordNew = mEdtNewPassword.getText().toString();
            String passwordNewRetyped = mEdtRetypeNewPassword.getText().toString();

            if (oldPassword.isEmpty() || passwordNew.isEmpty() || passwordNewRetyped.isEmpty()) {

                mViewModel.changePasswordListener.onFailure(MyApplication.getInstance().getResources()
                        .getString(R.string.fields_should_not_be_empty));

            } else {
                if (!password.equals(oldPassword)) {
                    mViewModel.changePasswordListener.onFailure(MyApplication.getInstance().getResources()
                            .getString(R.string.wrong_old_password));
                    return;
                }

                if (!passwordNew.equals(passwordNewRetyped)) {
                    mViewModel.changePasswordListener.onFailure(MyApplication.getInstance().getResources()
                            .getString(R.string.password_mismatches));
                    return;
                }

                mViewModel.getUserData(ChangePasswordFragment.this,username, passwordNew);
            }
        }
    }


    @Override
    public void onSuccess(String message) {
        Methods.toast(message);
        mSessionManager.logoutExecutive();
    }


    @Override
    public void onFailure(String message) {
        Methods.toast(message);
    }
}
