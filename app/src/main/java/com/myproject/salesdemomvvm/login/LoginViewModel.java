package com.myproject.salesdemomvvm.login;

import com.myproject.salesdemomvvm.R;
import com.myproject.salesdemomvvm.application.MyApplication;
import com.myproject.salesdemomvvm.repositories.AppRepository;
import com.myproject.salesdemomvvm.utils.SessionManager;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    LoginListener mLoginListener;

    void sendData( LoginActivity context, String userId, String password) {
        mLoginListener.onStarted();

        if (userId.isEmpty() || password.isEmpty()) {
            mLoginListener.onFailure("Both fields are mandatory");
            return;
        }

        LiveData<List<ExecutiveModel>> loginData = new AppRepository().loginExecutive(userId, password, "");
        if(loginData != null){
        loginData.observe(context, new Observer<List<ExecutiveModel>>() {
            @Override
            public void onChanged(List<ExecutiveModel> executiveModels) {
                ExecutiveModel model = executiveModels.get(0);
                SessionManager sessionManager = new SessionManager();
                sessionManager.createExecutiveSession(
                        model.getTid(),
                        model.getName(),
                        model.getContact(),
                        model.getAddress(),
                        model.getDesignation(),
                        model.getHire_date(),
                        model.getPhoto(),
                        model.getUsername(),
                        model.getPassword(),
                        model.getEmail(),
                        model.getToken(),
                        model.getRegion());
            }
        });

        mLoginListener.onSuccess("Welcome");
    }else {
            mLoginListener.onFailure(MyApplication.getInstance()
                    .getResources()
                    .getString(R.string.no_internet_connection));
        }
    }


}
