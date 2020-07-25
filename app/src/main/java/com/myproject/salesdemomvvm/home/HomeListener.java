package com.myproject.salesdemomvvm.home;

import java.util.ArrayList;

public interface HomeListener {

    void onHomeListGeneration(ArrayList<HomeModel> homeModels);
    void onSuccess(String url);
    void onFailure(String message);
}
