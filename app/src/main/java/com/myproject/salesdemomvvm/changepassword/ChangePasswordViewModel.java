package com.myproject.salesdemomvvm.changepassword;

import com.myproject.salesdemomvvm.R;
import com.myproject.salesdemomvvm.application.MyApplication;
import com.myproject.salesdemomvvm.repositories.AppRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class ChangePasswordViewModel extends ViewModel {

    ChangePasswordListener changePasswordListener;


    public void getUserData(ChangePasswordFragment context,String username, String password) {

        LiveData<String> responseData = new AppRepository().updatePassword(username,password);
        if(responseData == null){
            changePasswordListener.onFailure(MyApplication.getInstance().getResources()
                    .getString(R.string.no_internet_connection));
        }else{
            responseData.observe(context, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    changePasswordListener.onSuccess(s);
                }
            });
        }


    }

}
