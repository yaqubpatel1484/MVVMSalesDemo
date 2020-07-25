package com.myproject.salesdemomvvm.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myproject.salesdemomvvm.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private ArrayList<HomeModel> mArrayList;

    public HomeAdapter(ArrayList<HomeModel> arrayList){
        this.mArrayList = arrayList;
    }

    @Override
    public int getItemCount() {
        if(mArrayList != null){
            return mArrayList.size();
        }
        return 0;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_item_home,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        HomeModel model = mArrayList.get(position);
        holder.mTitle.setText(model.getTitle());
        holder.mImage.setBackgroundResource(model.getPath());

    }

     class MyViewHolder extends RecyclerView.ViewHolder{

         CircleImageView mImage;
         TextView mTitle;
         LinearLayout mLayout;

         MyViewHolder(@NonNull View itemView) {
            super(itemView);

             mImage =  itemView.findViewById(R.id.view_item_home_ivLogo);
             mTitle =  itemView.findViewById(R.id.view_item_home_tvTitle);
             mLayout =  itemView.findViewById(R.id.view_item_home_layout);

             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     mOnListItemClickListener.onItemClick(mArrayList.get(getAdapterPosition()).getId());
                 }
             });
        }
    }


    public interface OnListItemClickListener{
        void onItemClick(String id);
    }

    private OnListItemClickListener mOnListItemClickListener;

    public void setOnItemClickListener(OnListItemClickListener listener){
        this.mOnListItemClickListener = listener;
    }

}
