package com.myproject.salesdemomvvm.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.myproject.salesdemomvvm.R;
import com.myproject.salesdemomvvm.addclient.AddClientFragment;
import com.myproject.salesdemomvvm.allclients.AllClientsFragments;
import com.myproject.salesdemomvvm.application.MyApplication;
import com.myproject.salesdemomvvm.changepassword.ChangePasswordFragment;
import com.myproject.salesdemomvvm.profile.ProfileFragment;
import com.myproject.salesdemomvvm.utils.Methods;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HomeFragment extends Fragment implements HomeListener {

    private Context mContext;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ViewFlipper mSlider;
    private HomeAdapter mHomeAdapter;
    private ArrayList<HomeModel> mArrayList = new ArrayList<>();
    private HomeViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        init(rootView);
        initRecycler(rootView);
        return rootView;
    }

    private void init(View rootView) {
        mContext = MyApplication.getInstance();
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mViewModel.mHomeListener = this;

        mSwipeRefreshLayout = rootView.findViewById(R.id.home_fragment_swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new OnSwipeListener());
        mSlider = rootView.findViewById(R.id.home_fragment_slider);
    }

    private void initRecycler(View rootView) {

        RecyclerView mRecyclerView = rootView.findViewById(R.id.home_fragment_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mHomeAdapter = new HomeAdapter(mArrayList);
        mHomeAdapter.setOnItemClickListener(new ListItemClickListener());
        mRecyclerView.setAdapter(mHomeAdapter);

        getAllData();

    }

    @Override
    public void onHomeListGeneration(ArrayList<HomeModel> homeModels) {
        mArrayList.clear();
        mArrayList.addAll(homeModels);
        mHomeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess(String message) {
        ImageView image = new ImageView(mContext);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        image.setPadding(2, 2, 2, 2);

        Glide.with(mContext).load(message).into(image);
        mSlider.addView(image);
    }

    @Override
    public void onFailure(String message) {
        Methods.toast(message);
    }


    private class OnSwipeListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            getAllData();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }


    private void getAllData() {
        mViewModel.getSliderImages(HomeFragment.this);
        mViewModel.getList(HomeFragment.this);
    }


    private class ListItemClickListener implements HomeAdapter.OnListItemClickListener {
        @Override
        public void onItemClick(String id) {

            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch (id) {
                case "0":
                    fragmentTransaction.replace(R.id.base_container, new AddClientFragment())
                            .addToBackStack(null)
                            .commit();
                    break;
                case "1":
                    fragmentTransaction.replace(R.id.base_container, new AllClientsFragments())
                            .addToBackStack(null)
                            .commit();
                    break;
                case "2":
                    fragmentTransaction.replace(R.id.base_container, new ProfileFragment())
                            .addToBackStack(null)
                            .commit();
                    break;
                case "3":
                    fragmentTransaction.replace(R.id.base_container, new ChangePasswordFragment())
                            .addToBackStack(null)
                            .commit();
                    break;
                case "4":
                    Intent intentCall = new Intent(Intent.ACTION_DIAL);
                    intentCall.setData(Uri.parse("tel:" + "9028581484"));
                    startActivity(intentCall);
                    break;
                default:
                    break;
            }
        }
    }
}
