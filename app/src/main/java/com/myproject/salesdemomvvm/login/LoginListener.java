package com.myproject.salesdemomvvm.login;

public interface LoginListener {

     void onStarted();
     void onSuccess(String message);
     void onFailure(String message);

}
