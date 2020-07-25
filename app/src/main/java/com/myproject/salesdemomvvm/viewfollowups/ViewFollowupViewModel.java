package com.myproject.salesdemomvvm.viewfollowups;

import com.myproject.salesdemomvvm.R;
import com.myproject.salesdemomvvm.application.MyApplication;
import com.myproject.salesdemomvvm.repositories.AppRepository;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class ViewFollowupViewModel extends ViewModel {

     ViewFollowupListener mViewFollowupListener;

    public  void getFollowups(ViewFollowupFragment context, String ticketNo){
        LiveData<ArrayList<ViewFollowupModel>> followupsList = new AppRepository().getFollowup(ticketNo);
        if(followupsList != null){
            followupsList.observe(context, new Observer<ArrayList<ViewFollowupModel>>() {
                @Override
                public void onChanged(ArrayList<ViewFollowupModel> viewFollowupModels) {
                    mViewFollowupListener.onSuccess(viewFollowupModels);
                }
            });
        }else{
            mViewFollowupListener.onFailure(MyApplication.getInstance()
                    .getResources().getString(R.string.no_internet_connection));
        }
    }

}
