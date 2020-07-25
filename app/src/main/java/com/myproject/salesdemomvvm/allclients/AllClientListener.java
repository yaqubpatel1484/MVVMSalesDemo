package com.myproject.salesdemomvvm.allclients;

import java.util.ArrayList;

public interface AllClientListener {

    void onSuccess(ArrayList<AllClientsModel> allClientsList);
    void onFailure(String message);
}
