package com.myproject.salesdemomvvm.addfollowup;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.myproject.salesdemomvvm.R;
import com.myproject.salesdemomvvm.allclients.AllClientsModel;
import com.myproject.salesdemomvvm.application.MyApplication;
import com.myproject.salesdemomvvm.utils.FileUtils;
import com.myproject.salesdemomvvm.utils.Methods;
import com.myproject.salesdemomvvm.utils.SessionManager;
import com.myproject.salesdemomvvm.viewfollowups.ViewFollowupFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class AddFollowupFragment extends Fragment implements AddFollowupListener {

    private AddFollowupViewModel mViewModel;
    private EditText mEdtDescription, mEdtNextFollowupDate, mEdtFollowupStatus;
    private TextView mTvFile1Selected;
    private String mTicketNo, mClientName;
    private String mPdfPath = "", mSelectedDate = "", mMediaPath = "";
    private int i;
    private ProgressDialog mProgressDialog;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_followup_fragment, container, false);
        init(rootView);

        return rootView;
    }


    private void init(View rootView) {

        if (getArguments() != null) {

            mTicketNo = getArguments().getString(AllClientsModel.TICKET_NO);
            mClientName = getArguments().getString(AllClientsModel.COMP_NAME);

            TextView mTvTicketNo = rootView.findViewById(R.id.add_followup_tvTicketNo);
            String title = mTicketNo + "-" + mClientName;
            mTvTicketNo.setText(title);

        }

        mProgressDialog = new ProgressDialog(MyApplication.getInstance());

        mViewModel = new ViewModelProvider(this).get(AddFollowupViewModel.class);
        mViewModel.mAddFollowupListener = this;

        mEdtDescription = rootView.findViewById(R.id.add_followup_edtDescription);
        mEdtNextFollowupDate = rootView.findViewById(R.id.add_followup_edtNextFollowupDate);
        mEdtNextFollowupDate.setOnClickListener(new SelectDate());
        mEdtFollowupStatus = rootView.findViewById(R.id.add_followup_edtFollowupStatus);
        mEdtFollowupStatus.setOnClickListener(new SelectStatusClickListener());

        TextView mTxtSelectFile = rootView.findViewById(R.id.add_followup_tvSelectFile);
        mTxtSelectFile.setOnClickListener(new SelectFileClickListener());

        mTvFile1Selected = rootView.findViewById(R.id.add_followup_tvPdfOneSelected);

        Button mBtnSubmit = rootView.findViewById(R.id.add_followup_btnSubmit);
        mBtnSubmit.setOnClickListener(new BtnSubmitClickListener());

        checkPermissions();

    }

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.e("Permission", "Granted");
        } else {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(getActivity(), permissions, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission", "Granted");
            }
        } else {
            Methods.toast(getResources().getString(R.string.permission_denied));
        }
    }

    private class BtnSubmitClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {


            if (mEdtDescription.getText().toString().isEmpty()
                    || mEdtFollowupStatus.getText().toString().isEmpty()
                    || mEdtNextFollowupDate.getText().toString().isEmpty()) {

                Methods.toast(getResources().getString(R.string.star_fields_should_not_be_empty));

            } else {
                mProgressDialog.create();
                if (i == 1) {
                    LiveData<String> resultUrl = mViewModel.getPath(mMediaPath);
                    if (resultUrl != null) {
                        resultUrl.observe(AddFollowupFragment.this, new Observer<String>() {
                            @Override
                            public void onChanged(String s) {
                                mPdfPath = s;
                                addFollowup();
                            }
                        });
                    } else {
                        Methods.toast(getResources().getString(R.string.no_internet_connection));
                    }
                } else {

                    addFollowup();

                }
            }


        }
    }

    private void addFollowup() {

        SessionManager sessionManager = new SessionManager();
        HashMap<String, String> user = sessionManager.getExecutiveDetails();
        String mSesUsername = user.get(sessionManager.KEY_USERNAME);
        String mSesName = user.get(sessionManager.KEY_NAME);

        mViewModel.addFollowup(AddFollowupFragment.this, mTicketNo, mClientName,
                mEdtDescription.getText().toString(), mEdtFollowupStatus.getText().toString(),
                mSelectedDate, mPdfPath, mSesUsername, mSesName);
    }

    @Override
    public void onSuccess(String message) {
        mProgressDialog.dismiss();
        Methods.toast(message);
        FragmentManager fragmentManager = getParentFragmentManager();


        ViewFollowupFragment viewFollowupFragment = new ViewFollowupFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AllClientsModel.TICKET_NO, mTicketNo);
        bundle.putString(AllClientsModel.COMP_NAME, mClientName);

        viewFollowupFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.base_container, viewFollowupFragment)
                .addToBackStack(null)
                .commit();


    }

    @Override
    public void onFailure(String message) {
        Methods.toast(message);
        mProgressDialog.dismiss();
    }


    private class SelectStatusClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            LiveData<ArrayList<FollowupStatusModel>> resultList =
                    mViewModel.getStatus();
            if (resultList != null) {
                resultList.observe(AddFollowupFragment.this, new Observer<ArrayList<FollowupStatusModel>>() {
                    @Override
                    public void onChanged(ArrayList<FollowupStatusModel> arrayListStatus) {
                        SelectStatusDialog selectStatusDialog = new SelectStatusDialog(getContext(), arrayListStatus);
                        selectStatusDialog.show();
                        selectStatusDialog.SetOnItemClick(new StatusSelectListener());
                    }
                });
            } else {
                Methods.toast(getResources().getString(R.string.no_internet_connection));
            }


        }
    }

    private class StatusSelectListener implements SelectStatusDialog.SetOnItemClickListener {
        @Override
        public void onItemClick(String status) {
            mEdtFollowupStatus.setText(status);
        }
    }


    private class SelectDate implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(),
                    R.style.AppBlackTheme,
                    dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            datePicker.setCancelable(false);
            datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePicker.setTitle("Select next followup date");
            datePicker.show();

        }
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String Year = String.valueOf(year);
            String Month = String.valueOf(month + 1);
            String Day = String.valueOf(dayOfMonth);
            if (Month.length() == 1) {
                Month = "0" + Month;
            }
            if (Day.length() == 1) {
                Day = "0" + Month;
            }

            mSelectedDate = Year + "-" + Month + "-" + Day;
            mEdtNextFollowupDate.setText(mSelectedDate);
        }
    };


    private class SelectFileClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select PDF"), 1);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {

            i = 1;
            mTvFile1Selected.setVisibility(View.VISIBLE);
            Methods.toast("File Selected");

            assert data != null;
            Uri selectedUri = data.getData();
            mMediaPath = FileUtils.getRealPath(getActivity(), selectedUri);

        }
    }
}
