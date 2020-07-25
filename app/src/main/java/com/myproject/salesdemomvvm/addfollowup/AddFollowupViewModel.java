package com.myproject.salesdemomvvm.addfollowup;

import com.myproject.salesdemomvvm.R;
import com.myproject.salesdemomvvm.application.MyApplication;
import com.myproject.salesdemomvvm.repositories.AppRepository;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class AddFollowupViewModel extends ViewModel {

    AddFollowupListener mAddFollowupListener;

    void addFollowup(AddFollowupFragment context, String ticketNo, String clientName,
                     String description, String status, String nextFollowupDate,
                     String url, String byUserName, String byName) {

        LiveData<String> response = new AppRepository().addFollowup(ticketNo, clientName, description,
                status, nextFollowupDate, url, byUserName, byName);

        if (response != null) {
            response.observe(context, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    mAddFollowupListener.onSuccess(s);
                }
            });
        } else {
            mAddFollowupListener.onFailure(MyApplication.getInstance().getResources()
                    .getString(R.string.no_internet_connection));
        }
    }

   public LiveData<ArrayList<FollowupStatusModel>> getStatus() {
       return new AppRepository().getStatusList();
   }


    public LiveData<String> getPath(String mediaPath) {
        return new AppRepository().uploadPDF(mediaPath);
    }


}
