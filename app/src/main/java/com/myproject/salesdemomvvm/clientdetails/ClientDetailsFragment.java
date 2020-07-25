package com.myproject.salesdemomvvm.clientdetails;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.myproject.salesdemomvvm.R;
import com.myproject.salesdemomvvm.allclients.AllClientsModel;

import androidx.fragment.app.Fragment;


public class ClientDetailsFragment extends Fragment {

    private AllClientsModel model;
    private TextView mTvCompanyName, mTvContactPerson, mTvContactNo, mTvTicketNo, mTvStatus;
    private TextView mTvDate, mTvNextFollowup, mTvAddress, mTvArea, mTvCity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.client_details_fragment, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View rootView) {

        if (getArguments() != null) {
            model = getArguments().getParcelable(AllClientsModel.CLIENT);

            mTvCompanyName = rootView.findViewById(R.id.client_details_tvCompanyName);
            mTvContactPerson = rootView.findViewById(R.id.client_details_tvPersonName);
            mTvContactNo = rootView.findViewById(R.id.client_details_tvContactNo);
            mTvTicketNo = rootView.findViewById(R.id.client_details_tvTicketNo);
            mTvStatus = rootView.findViewById(R.id.client_details_tvStatus);
            mTvDate = rootView.findViewById(R.id.client_details_tvDate);
            mTvNextFollowup = rootView.findViewById(R.id.client_details_tvNextFollowup);
            mTvAddress = rootView.findViewById(R.id.client_details_tvAddress);
            mTvArea = rootView.findViewById(R.id.client_details_tvArea);
            mTvCity = rootView.findViewById(R.id.client_details_tvCity);

            Button mBtnCall = rootView.findViewById(R.id.client_details_btnCall);
            mBtnCall.setOnClickListener(new OnCallClickListener());

            setData();

        }
    }

    private void setData() {

        mTvCompanyName.setText(model.getCompany_name());
        mTvContactPerson.setText(model.getName());
        mTvContactNo.setText(model.getContact());
        mTvTicketNo.setText(model.getTicket_no());
        mTvStatus.setText(model.getStatus());
        mTvDate.setText(model.getDate());
        mTvNextFollowup.setText(model.getNext_followup());
        mTvAddress.setText(model.getAddress());
        mTvArea.setText(model.getArea());
        mTvCity.setText(model.getCity());

    }

    private class OnCallClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intentCall = new Intent(Intent.ACTION_DIAL);
            intentCall.setData(Uri.parse("tel:" + model.getContact()));
            startActivity(intentCall);
        }
    }
}
