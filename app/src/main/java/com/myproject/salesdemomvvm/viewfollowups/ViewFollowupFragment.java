package com.myproject.salesdemomvvm.viewfollowups;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myproject.salesdemomvvm.R;
import com.myproject.salesdemomvvm.addfollowup.AddFollowupFragment;
import com.myproject.salesdemomvvm.allclients.AllClientsModel;
import com.myproject.salesdemomvvm.application.MyApplication;
import com.myproject.salesdemomvvm.utils.Methods;
import com.myproject.salesdemomvvm.utils.SessionManager;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ViewFollowupFragment extends Fragment implements ViewFollowupListener {

    private ViewFollowupViewModel mViewModel;
    private String mTicketNo, mCompanyName;
    private ProgressDialog mProgressDialog;
    private ArrayList<ViewFollowupModel> mArrayList = new ArrayList<>();
    private ViewFollowupAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_follwup_fragment, container, false);
        setHasOptionsMenu(true);
        init(rootView);
        initRecycler(rootView);
        return rootView;
    }

    private void init(View rootView) {
        if (getArguments() != null) {
            mTicketNo = getArguments().getString(AllClientsModel.TICKET_NO);
            mCompanyName = getArguments().getString(AllClientsModel.COMP_NAME);
            TextView tvTitle = rootView.findViewById(R.id.view_followup_tvTicketNo);
            String title = mTicketNo + "-" + mCompanyName;
            tvTitle.setText(title);
        }

        mProgressDialog = new ProgressDialog(MyApplication.getInstance());

        mViewModel = new ViewModelProvider(this).get(ViewFollowupViewModel.class);
        mViewModel.mViewFollowupListener = this;
    }

    private void initRecycler(View rootView) {

        mSwipeRefreshLayout = rootView.findViewById(R.id.view_followup_swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SetRefreshListener());

        RecyclerView mRecyclerView = rootView.findViewById(R.id.view_followup_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ViewFollowupAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.SetOnClickListener(new ItemClickListener());

        getAllFollowups();
    }

    private void getAllFollowups() {
        mViewModel.getFollowups(ViewFollowupFragment.this, mTicketNo);
    }

    private class ItemClickListener implements ViewFollowupAdapter.OnItemClickListener {

        @Override
        public void onViewPdf(String url) {
            openPdf(url);
        }
    }

    private void openPdf(String url) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private class SetRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            getAllFollowups();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onSuccess(ArrayList<ViewFollowupModel> followupList) {
        mProgressDialog.dismiss();
        mArrayList.clear();
        mArrayList.addAll(followupList);
        if (mArrayList.size() > 0) {
            mAdapter.notifyDataSetChanged();
        } else {
            Methods.toast(getResources().getString(R.string.no_records_found));
        }
    }

    @Override
    public void onFailure(String message) {
        Methods.toast(message);
        mProgressDialog.dismiss();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_add).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add) {

            FragmentManager fragmentManager = getParentFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putString(AllClientsModel.TICKET_NO, mTicketNo);
            bundle.putString(AllClientsModel.COMP_NAME, mCompanyName);

            AddFollowupFragment addFollowupFragment = new AddFollowupFragment();
            addFollowupFragment.setArguments(bundle);

            fragmentManager.beginTransaction().add(R.id.base_container, addFollowupFragment)
                    .addToBackStack("").commit();


        } else if (id == R.id.action_logout) {
            new SessionManager().logoutExecutive();
        }

        return super.onOptionsItemSelected(item);
    }
}
