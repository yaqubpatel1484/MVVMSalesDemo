package com.myproject.salesdemomvvm.viewfollowups;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myproject.salesdemomvvm.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewFollowupAdapter extends RecyclerView.Adapter {

    private ArrayList<ViewFollowupModel> mArrayList;

    ViewFollowupAdapter(ArrayList<ViewFollowupModel> arrayList) {
        this.mArrayList = arrayList;
    }


    class ViewHolder1 extends RecyclerView.ViewHolder {

        TextView mTvDescription, mTvStatus, mTvNextDate, mTvFollowupBy, mTvDateTime;

        ViewHolder1(@NonNull View itemView) {
            super(itemView);

            mTvDescription = itemView.findViewById(R.id.view_followup_list_item_tvDescription);
            mTvStatus = itemView.findViewById(R.id.view_followup_list_item_tvStatus);
            mTvNextDate = itemView.findViewById(R.id.view_followup_list_item_tvNextFollowupDate);
            mTvFollowupBy = itemView.findViewById(R.id.view_followup_list_item_tvFollowupBy);
            mTvDateTime = itemView.findViewById(R.id.view_followup_list_item_tvDateTime);

        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {

        TextView mTvDescription, mTvStatus, mTvNextDate, mTvFollowupBy, mTvDateTime;
        FloatingActionButton mFabViewQuotation;

        ViewHolder2(@NonNull View itemView) {
            super(itemView);

            mTvDescription = itemView.findViewById(R.id.view_followup_list_item_1_tvDescription);
            mTvStatus = itemView.findViewById(R.id.view_followup_list_item_1_tvStatus);
            mTvNextDate = itemView.findViewById(R.id.view_followup_list_item_1_tvNextFollowupDate);
            mTvFollowupBy = itemView.findViewById(R.id.view_followup_list_item_1_tvFollowupBy);
            mTvDateTime = itemView.findViewById(R.id.view_followup_list_item_1_tvDateTime);
            mFabViewQuotation = itemView.findViewById(R.id.view_followup_list_item_1_fabQuotation);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onViewPdf(mArrayList.get(getAdapterPosition()).getPdf_url());
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        if (mArrayList != null) {
            return mArrayList.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {

        if (mArrayList.get(position).getPdf_url().equals("")) {
            return 1;
        } else {
            return 2;
        }


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == 1) {
            view = layoutInflater.inflate(R.layout.view_followup_list_item, parent, false);
            return new ViewHolder1(view);
        } else {
            view = layoutInflater.inflate(R.layout.view_followup_list_item_1, parent, false);
            return new ViewHolder2(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewFollowupModel model = mArrayList.get(position);

        if (holder instanceof ViewHolder1) {
            ((ViewHolder1) holder).mTvDescription.setText(model.getDescription());
            ((ViewHolder1) holder).mTvStatus.setText(model.getStatus());
            ((ViewHolder1) holder).mTvNextDate.setText(model.getNext_followup_date());
            String followupBy = "By: " + model.getBy_name();
            ((ViewHolder1) holder).mTvFollowupBy.setText(followupBy);
            ((ViewHolder1) holder).mTvDateTime.setText(model.getDate());
        } else if (holder instanceof ViewHolder2) {
            ((ViewHolder2) holder).mTvDescription.setText(model.getDescription());
            ((ViewHolder2) holder).mTvStatus.setText(model.getStatus());
            ((ViewHolder2) holder).mTvNextDate.setText(model.getNext_followup_date());
            String followupBy = "By: " + model.getBy_name();
            ((ViewHolder2) holder).mTvFollowupBy.setText(followupBy);
            ((ViewHolder2) holder).mTvDateTime.setText(model.getDate());
        }
    }


    public interface OnItemClickListener {
        void onViewPdf(String url);
    }

    private OnItemClickListener mOnItemClickListener;

    public void SetOnClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

}
