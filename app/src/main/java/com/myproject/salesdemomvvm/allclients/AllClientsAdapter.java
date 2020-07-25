package com.myproject.salesdemomvvm.allclients;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myproject.salesdemomvvm.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AllClientsAdapter extends RecyclerView.Adapter {

    private ArrayList<AllClientsModel> mArrayList;

    AllClientsAdapter(ArrayList<AllClientsModel> arrayList) {
        this.mArrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        if (viewType == 1) {
            view = layoutInflater.inflate(R.layout.view_all_client_item_1, parent, false);
            return new ViewHolder1(view);
        } else {
            view = layoutInflater.inflate(R.layout.view_all_client_item_2, parent, false);
            return new ViewHolder2(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        AllClientsModel model = mArrayList.get(position);
        if (holder instanceof ViewHolder1) {
            ((ViewHolder1) holder).mTvCompanyName.setText(model.getCompany_name());
            ((ViewHolder1) holder).mTvTicketNo.setText(model.getTicket_no());
            ((ViewHolder1) holder).mTvPersonName.setText(model.getName());
            ((ViewHolder1) holder).mTvTicketStatus.setText(model.getStatus());
            ((ViewHolder1) holder).mTvNextFollowup.setText(model.getNext_followup());
            String address = model.getAddress() + " " + model.getArea() + " " + model.getCity();
            ((ViewHolder1) holder).mTvAddress.setText(address);
            ((ViewHolder1) holder).mTvDate.setText(model.getDate());
        } else {
            ((ViewHolder2) holder).mTvCompanyName.setText(model.getCompany_name());
            ((ViewHolder2) holder).mTvTicketNo.setText(model.getTicket_no());
            ((ViewHolder2) holder).mTvPersonName.setText(model.getName());
            ((ViewHolder2) holder).mTvTicketStatus.setText(model.getStatus());
            ((ViewHolder2) holder).mTvNextFollowup.setText(model.getNext_followup());
            String address = model.getAddress() + " " + model.getArea() + " " + model.getCity();
            ((ViewHolder2) holder).mTvAddress.setText(address);
            ((ViewHolder2) holder).mNotifyCount.setText(model.getNotify_count());
            ((ViewHolder2) holder).mTvDate.setText(model.getDate());
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
        if (mArrayList.get(position).getNotify_count().equals("0")) {
            return 1;
        } else {
            return 2;
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {

        TextView mTvCompanyName, mTvTicketNo, mTvPersonName, mTvNextFollowup, mTvTicketStatus, mTvAddress, mTvDate;
        CardView mCartView;

        ViewHolder1(@NonNull View itemView) {
            super(itemView);

            mTvCompanyName = itemView.findViewById(R.id.view_all_clients_item_1_tvCompanyName);
            mTvTicketNo = itemView.findViewById(R.id.view_all_clients_item_1_tvTicketNo);
            mTvPersonName = itemView.findViewById(R.id.view_all_clients_item_1_tvPersonName);
            mTvTicketStatus = itemView.findViewById(R.id.view_all_clients_item_1_tvStatus);
            mTvNextFollowup = itemView.findViewById(R.id.view_all_clients_item_1_tvNextFollowupDate);
            mTvAddress = itemView.findViewById(R.id.view_all_clients_item_1_tvAddress);
            mTvDate = itemView.findViewById(R.id.view_all_clients_item_1_tvDate);
            mCartView = itemView.findViewById(R.id.view_all_clients_item_1_cardView);

            mTvCompanyName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSetOnItemClickListener != null) {
                        mSetOnItemClickListener.onDetailsClick(mArrayList.get(getAdapterPosition()));
                    }
                }
            });

            mCartView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSetOnItemClickListener != null) {
                        mSetOnItemClickListener.onItemClick(
                                mArrayList.get(getAdapterPosition()).getTicket_no(),
                                mArrayList.get(getAdapterPosition()).getCompany_name());
                    }
                }
            });
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {

        TextView mTvCompanyName, mTvTicketNo, mTvPersonName, mTvNextFollowup, mTvTicketStatus, mTvAddress, mTvDate;
        CardView mCartView;
        TextView mNotifyCount;

        ViewHolder2(@NonNull View itemView) {
            super(itemView);

            mTvCompanyName = itemView.findViewById(R.id.view_all_clients_item_2_tvCompanyName);
            mTvTicketNo = itemView.findViewById(R.id.view_all_clients_item_2_tvTicketNo);
            mTvPersonName = itemView.findViewById(R.id.view_all_clients_item_2_tvPersonName);
            mTvTicketStatus = itemView.findViewById(R.id.view_all_clients_item_2_tvStatus);
            mTvNextFollowup = itemView.findViewById(R.id.view_all_clients_item_2_tvNextFollowupDate);
            mTvAddress = itemView.findViewById(R.id.view_all_clients_item_2_tvAddress);
            mTvDate = itemView.findViewById(R.id.view_all_clients_item_2_tvDate);
            mNotifyCount = itemView.findViewById(R.id.view_all_clients_item_2_tvUnreadFollowupCount);
            mCartView = itemView.findViewById(R.id.view_all_clients_item_2_cardView);

            mTvCompanyName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSetOnItemClickListener.onDetailsClick(mArrayList.get(getAdapterPosition()));
                }
            });

            mCartView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSetOnItemClickListener.onItemClick(
                            mArrayList.get(getAdapterPosition()).getTicket_no(),
                            mArrayList.get(getAdapterPosition()).getCompany_name());
                }
            });
        }
    }

    interface SetOnItemClickListener {
        void onDetailsClick(AllClientsModel clientsModel);

        void onItemClick(String ticket_no, String comp_name);
    }

    private SetOnItemClickListener mSetOnItemClickListener;

    public void ItemClick(SetOnItemClickListener setOnItemClickListener) {
        this.mSetOnItemClickListener = setOnItemClickListener;
    }

}
