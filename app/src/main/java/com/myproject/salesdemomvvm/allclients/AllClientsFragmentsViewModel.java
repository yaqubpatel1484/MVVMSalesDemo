package com.myproject.salesdemomvvm.allclients;

import android.util.Log;

import com.myproject.salesdemomvvm.R;
import com.myproject.salesdemomvvm.application.MyApplication;
import com.myproject.salesdemomvvm.repositories.AppRepository;
import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class AllClientsFragmentsViewModel extends ViewModel {

     AllClientListener mAllClientListener;

     void getClients(AllClientsFragments context,String username, String search_entity){

        LiveData<ArrayList<AllClientsModel>> responseData = new AppRepository().getClients(username,search_entity);

        if(responseData == null){
            mAllClientListener.onFailure(MyApplication.getInstance()
                    .getResources().getString(R.string.no_internet_connection));
        }else{
            responseData.observe(context, new Observer<ArrayList<AllClientsModel>>() {
                @Override
                public void onChanged(ArrayList<AllClientsModel> allClientsModels) {
                   mAllClientListener.onSuccess(allClientsModels);
                }
            });
        }
    }


    void addClientRoom(ArrayList<AllClientsModel> arrayList){
         for (int i = 0; i< arrayList.size();i++){
             AllClientsModel model = arrayList.get(i);

              String res =  new AppRepository().insertClient(model);
             Log.e("tag","-"+res+i);
         }
    }

}
