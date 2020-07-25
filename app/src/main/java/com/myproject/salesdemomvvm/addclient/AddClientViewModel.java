package com.myproject.salesdemomvvm.addclient;

import com.myproject.salesdemomvvm.R;
import com.myproject.salesdemomvvm.application.MyApplication;
import com.myproject.salesdemomvvm.repositories.AppRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class AddClientViewModel extends ViewModel {

    public AddClientListener mAddClientListener;

    public void addClientData(AddClientFragment context,String executive_username, String executive_name,
                              String company_name, String name, String contact,
                              String name1, String contact1, String name2, String contact2,
                              String address, String area, String city, String pincode){

        final LiveData<String> responseData = new AppRepository().addClient(executive_username, executive_name,
                company_name, name, contact, name1, contact1, name2, contact2, address,
                area, city, pincode);

        if(responseData != null){
            responseData.observe(context, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    mAddClientListener.onSuccess(s);
                }
            });
        }else{
            mAddClientListener.onFailure(MyApplication.getInstance().getResources()
                    .getString(R.string.no_internet_connection));
        }

    }

}
