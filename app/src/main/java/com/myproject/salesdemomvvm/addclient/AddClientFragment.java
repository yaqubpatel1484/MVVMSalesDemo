package com.myproject.salesdemomvvm.addclient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.myproject.salesdemomvvm.R;
import com.myproject.salesdemomvvm.application.MyApplication;
import com.myproject.salesdemomvvm.utils.Methods;
import com.myproject.salesdemomvvm.utils.SessionManager;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

public class AddClientFragment extends Fragment implements AddClientListener {

    private AddClientViewModel mViewModel;

    private EditText mEdtCompanyName, mEdtContactPerson, mEdtContactNo, mEdtContactPerson1, mEdtContactNo1;
    private EditText mEdtContactPerson2, mEdtContactNo2, mEdtAddress, mEdtArea, mEdtCity, mEdtPincode;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_client_fragment, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View rootView) {
        mViewModel = new ViewModelProvider(this).get(AddClientViewModel.class);
        mViewModel.mAddClientListener = this;

        mEdtCompanyName = rootView.findViewById(R.id.addClient_edtCompanyName);
        mEdtContactPerson = rootView.findViewById(R.id.addClient_edtPersonName);
        mEdtContactNo = rootView.findViewById(R.id.addClient_edtContactNo);
        mEdtContactPerson1 = rootView.findViewById(R.id.addClient_edtContactPerson1);
        mEdtContactNo1 = rootView.findViewById(R.id.addClient_edtContactNo1);
        mEdtContactPerson2 = rootView.findViewById(R.id.addClient_edtContactPerson2);
        mEdtContactNo2 = rootView.findViewById(R.id.addClient_edtContactNo2);
        mEdtAddress = rootView.findViewById(R.id.addClient_edtAddress);
        mEdtArea = rootView.findViewById(R.id.addClient_edtArea);
        mEdtCity = rootView.findViewById(R.id.addClient_edtCity);
        mEdtPincode = rootView.findViewById(R.id.addClient_edtPincode);

        Button mBtnSubmit = rootView.findViewById(R.id.addClient_btnSubmit);
        mBtnSubmit.setOnClickListener(new OnSubmitClickListener());

    }

    private class OnSubmitClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if (mEdtCompanyName.getText().toString().isEmpty()
                    || mEdtContactPerson.getText().toString().isEmpty()
                    || mEdtContactPerson.getText().toString().isEmpty()
                    || mEdtContactNo.getText().toString().isEmpty()
                    || mEdtArea.getText().toString().isEmpty()
                    || mEdtCity.getText().toString().isEmpty()) {

                mViewModel.mAddClientListener.onFailure(MyApplication.getInstance().getResources()
                        .getString(R.string.star_fields_should_not_be_empty));
                        return;
            }


            SessionManager sessionManager = new SessionManager();
            HashMap<String, String> user = sessionManager.getExecutiveDetails();

            mViewModel.addClientData(AddClientFragment.this,
                    user.get(sessionManager.KEY_USERNAME),
                    user.get(sessionManager.KEY_NAME),
                    mEdtCompanyName.getText().toString(),
                    mEdtContactPerson.getText().toString(),
                    mEdtContactNo.getText().toString(),
                    mEdtContactPerson1.getText().toString(),
                    mEdtContactNo1.getText().toString(),
                    mEdtContactPerson2.getText().toString(),
                    mEdtContactNo2.getText().toString(),
                    mEdtAddress.getText().toString(),
                    mEdtArea.getText().toString(),
                    mEdtCity.getText().toString(),
                    mEdtPincode.getText().toString());

        }
    }

    @Override
    public void onSuccess(String message) {
        Methods.toast(message);
        FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.popBackStack();
            fragmentManager.beginTransaction().remove(this).commit();
    }

    @Override
    public void onFailure(String message) {
        Methods.toast(message);
    }
}
