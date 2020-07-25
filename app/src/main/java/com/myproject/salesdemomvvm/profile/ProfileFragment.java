package com.myproject.salesdemomvvm.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myproject.salesdemomvvm.R;
import com.myproject.salesdemomvvm.application.MyApplication;
import com.myproject.salesdemomvvm.utils.SessionManager;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {


    private TextView mTvUsername, mTvName, mTvContact, mTvDesignation, mTvJoiningDate, mTvEmail, mTvAddress;
    private CircleImageView mIvProfile;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.profile_fragment, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View rootView) {



        mIvProfile = rootView.findViewById(R.id.executive_profile_ivProfile);
        mTvUsername = rootView.findViewById(R.id.executive_profile_tvUsernme);
        mTvName = rootView.findViewById(R.id.executive_profile_tvName);
        mTvContact = rootView.findViewById(R.id.executive_profile_tvContact);
        mTvDesignation = rootView.findViewById(R.id.executive_profile_tvDesignation);
        mTvJoiningDate = rootView.findViewById(R.id.executive_profile_tvJoiningDate);
        mTvEmail = rootView.findViewById(R.id.executive_profile_tvEmail);
        mTvAddress = rootView.findViewById(R.id.executive_profile_tvAddress);

        getData();

    }

    private void getData() {

        SessionManager sessionManager = new SessionManager();
        HashMap<String,String> user = sessionManager.getExecutiveDetails();

        Log.e("phpto","-"+user.get(sessionManager.KEY_PHOTO));
        Glide.with(MyApplication.getInstance()).load(user.get(sessionManager.KEY_PHOTO)).into(mIvProfile);

        mTvUsername.setText(user.get(sessionManager.KEY_USERNAME));
        mTvName.setText(user.get(sessionManager.KEY_NAME));
        mTvContact.setText(user.get(sessionManager.KEY_CONTACT));
        mTvDesignation.setText(user.get(sessionManager.KEY_DESIGNATION));
        mTvJoiningDate.setText(user.get(sessionManager.KEY_HIRE_DATE));
        mTvEmail.setText(user.get(sessionManager.KEY_EMAIL));
        mTvAddress.setText(user.get(sessionManager.KEY_ADDRESS));

    }

}
