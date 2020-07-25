package com.myproject.salesdemomvvm.addfollowup;

import android.app.Dialog;
import android.content.Context;

import com.myproject.salesdemomvvm.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SelectStatusDialog extends Dialog {

    public SelectStatusDialog(Context context, ArrayList<FollowupStatusModel> arrayList){
        super(context);
        setContentView(R.layout.followup_status_dialog);

        RecyclerView recyclerView = findViewById(R.id.followup_status_dialog_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        FollowupStatusAdapter adapter = new FollowupStatusAdapter(arrayList);
        adapter.SetOnItemClick(new ItemClicked());
        recyclerView.setAdapter(adapter);

    }

    private class ItemClicked implements FollowupStatusAdapter.SetOnItemClickListener{
        @Override
        public void onItemClick(String status) {
            mSetOnItemClickListener.onItemClick(status);
            dismiss();
        }
    }

    public interface SetOnItemClickListener{
        void onItemClick(String status);
    }

    private SetOnItemClickListener mSetOnItemClickListener;

    public void SetOnItemClick(SetOnItemClickListener onItemViewClickListener) {
        mSetOnItemClickListener = onItemViewClickListener;
    }

}
