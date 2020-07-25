package com.myproject.salesdemomvvm.home;

public class HomeModel {

    private String id,title,notify_count;
    private int path;

    public HomeModel(String id,int imagePath, String title,String notify_count) {
        this.id = id;
        this.path = imagePath;
        this.title = title;
        this.notify_count = notify_count;
    }


    public String getId() {
        return id;
    }

    public int getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public String getNotify_count() {
        return notify_count;
    }

}
