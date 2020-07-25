package com.myproject.salesdemomvvm.allclients;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myproject.salesdemomvvm.R;
import com.myproject.salesdemomvvm.application.MyApplication;
import com.myproject.salesdemomvvm.clientdetails.ClientDetailsFragment;
import com.myproject.salesdemomvvm.utils.Methods;
import com.myproject.salesdemomvvm.utils.SessionManager;
import com.myproject.salesdemomvvm.viewfollowups.ViewFollowupFragment;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class AllClientsFragments extends Fragment implements AllClientListener {

    private AllClientsFragmentsViewModel mViewModel;
    private ArrayList<AllClientsModel> mArrayList = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private FloatingActionButton mFabSearch;
    private LinearLayout mLayoutSearch;
    private EditText mEdtSearchEntity;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private AllClientsAdapter mAllClientsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.all_clients_fragment, container, false);
        init(rootView);
        initRecyclerView(rootView);
        return rootView;
    }


    private void init(View rootView) {
        mViewModel = new ViewModelProvider(this).get(AllClientsFragmentsViewModel.class);
        mViewModel.mAllClientListener = this;

        mProgressDialog = new ProgressDialog(MyApplication.getInstance());

        mFabSearch = rootView.findViewById(R.id.all_clients_fabSearch);
        mFabSearch.setOnClickListener(new FabSearchClickListener());

        mLayoutSearch = rootView.findViewById(R.id.all_clients_layoutSearch);
        mEdtSearchEntity = rootView.findViewById(R.id.all_clients_edtEntity);
        Button mBtnSearch = rootView.findViewById(R.id.all_clients_btnSearch);
        mBtnSearch.setOnClickListener(new BtnSearchListener());

        mSwipeRefreshLayout = rootView.findViewById(R.id.all_clients_swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new RefreshListener());
    }

    private void initRecyclerView(View rootView) {

        RecyclerView recyclerView = rootView.findViewById(R.id.all_clients_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                MyApplication.getInstance(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAllClientsAdapter = new AllClientsAdapter(mArrayList);
        recyclerView.setAdapter(mAllClientsAdapter);

        mAllClientsAdapter.ItemClick(new AllClientsItemClickListener());

        getAllData();

    }

    private void getAllData() {
        mProgressDialog.create();
        SessionManager sessionManager = new SessionManager();
        HashMap<String, String> user = sessionManager.getExecutiveDetails();
        mViewModel.getClients(AllClientsFragments.this,
                user.get(sessionManager.KEY_USERNAME),
                mEdtSearchEntity.getText().toString());

    }

    private class RefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            getAllData();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private class FabSearchClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mLayoutSearch.setVisibility(View.VISIBLE);
            mEdtSearchEntity.requestFocus();
            mFabSearch.setVisibility(View.GONE);
        }
    }

    private class BtnSearchListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (mEdtSearchEntity.getText().toString().isEmpty()) {
                mViewModel.mAllClientListener.onFailure(MyApplication.getInstance()
                        .getResources().getString(R.string.enter_for_search));

            } else {
                getAllData();
            }
        }
    }

    @Override
    public void onSuccess(ArrayList<AllClientsModel> allClientsList) {
        if (allClientsList.size() > 0) {
            mArrayList.clear();
            mArrayList.addAll(allClientsList);
            mAllClientsAdapter.notifyDataSetChanged();
            mProgressDialog.dismiss();
        //    mViewModel.addClientRoom(mArrayList);
        }else{
            Methods.toast(getResources().getString(R.string.no_records_found));
        }

    }

    @Override
    public void onFailure(String message) {
        Methods.toast(message);
        mProgressDialog.dismiss();
    }


    private class AllClientsItemClickListener implements AllClientsAdapter.SetOnItemClickListener {

        FragmentManager fragmentManager = getParentFragmentManager();
        Bundle bundle = new Bundle();

        @Override
        public void onDetailsClick(AllClientsModel clientsModel) {

            ClientDetailsFragment clientDetailsFragment = new ClientDetailsFragment();
            bundle.putParcelable(AllClientsModel.CLIENT, clientsModel);
            clientDetailsFragment.setArguments(bundle);

            if (fragmentManager != null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.base_container, clientDetailsFragment)
                        .addToBackStack(null)
                        .commit();
            }

        }

        @Override
        public void onItemClick(String ticket_no, String comp_name) {

            ViewFollowupFragment viewFollowupFragment = new ViewFollowupFragment();

            bundle.putString(AllClientsModel.TICKET_NO, ticket_no);
            bundle.putString(AllClientsModel.COMP_NAME, comp_name);

            viewFollowupFragment.setArguments(bundle);
            if (fragmentManager != null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.base_container, viewFollowupFragment)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }
}
