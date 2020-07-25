package com.myproject.salesdemomvvm.home;

import com.myproject.salesdemomvvm.R;
import com.myproject.salesdemomvvm.application.MyApplication;
import com.myproject.salesdemomvvm.repositories.AppRepository;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    HomeListener mHomeListener;

    public void getSliderImages(HomeFragment context){
            final LiveData<ArrayList<SliderModel>> sliderData = new AppRepository().getSlider();
            if(sliderData != null) {
                sliderData.observe(context, new Observer<List<SliderModel>>() {
                    @Override
                    public void onChanged(List<SliderModel> sliderModels) {
                        setSlider(sliderModels);
                    }
                });
            }else {
                mHomeListener .onFailure(MyApplication.getInstance()
                        .getResources()
                        .getString(R.string.no_internet_connection));
            }
    }

    private void setSlider(List<SliderModel> sliderModels) {

        for(int i= 0 ; i< sliderModels.size();i++){
            SliderModel model = sliderModels.get(i);
            mHomeListener.onSuccess(model.getUrl());
        }
    }


    public void getList(HomeFragment context){
        LiveData<ArrayList<HomeModel>> homeData = new AppRepository().getHomeList();
        homeData.observe(context, new Observer<ArrayList<HomeModel>>() {
            @Override
            public void onChanged(ArrayList<HomeModel> homeModels) {
                mHomeListener.onHomeListGeneration(homeModels);
            }
        });
    }


}
