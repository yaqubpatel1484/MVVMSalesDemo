package com.myproject.salesdemomvvm.viewfollowups;

import java.util.ArrayList;

public interface ViewFollowupListener {
    void onSuccess(ArrayList<ViewFollowupModel> followupList);
    void onFailure(String message);
}
