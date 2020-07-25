package com.myproject.salesdemomvvm.addfollowup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myproject.salesdemomvvm.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FollowupStatusAdapter extends RecyclerView.Adapter<FollowupStatusAdapter.MyViewHolder> {
        ArrayList<FollowupStatusModel> arrayList  ;
    FollowupStatusAdapter(ArrayList<FollowupStatusModel> arrayList){
        this.arrayList = arrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvStatus = itemView.findViewById(R.id.view_followup_status_tvStatus);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mSetOnItemClickListener != null) {
                        mSetOnItemClickListener.onItemClick(arrayList.get(getAdapterPosition()).getStatus());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        if(arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_followup_status,null);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        FollowupStatusModel model = arrayList.get(position);
        holder.tvStatus.setText(model.getStatus());

    }


    public interface SetOnItemClickListener{
        void onItemClick(String status);
    }

    private SetOnItemClickListener mSetOnItemClickListener;

    public void SetOnItemClick(SetOnItemClickListener onItemViewClickListener) {
        mSetOnItemClickListener = onItemViewClickListener;
    }
}
