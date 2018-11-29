package com.gr8apes.weatherapp_takehomeexam.presentation.fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.gr8apes.weatherapp_takehomeexam.presentation.activity.BaseActivity;
import com.gr8apes.weatherapp_takehomeexam.presentation.contract.LoadingView;
import com.gr8apes.weatherapp_takehomeexam.presentation.dialog.CustomProgressDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment extends Fragment implements LoadingView {

    public Context mContext;
    private Unbinder mUnbinder;
    protected CustomProgressDialog mCustomProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        mContext = getActivity();
        return inflater.inflate(getFragmentLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        mCustomProgressDialog = CustomProgressDialog.build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mUnbinder.unbind();
    }

    protected abstract int getFragmentLayoutId();

    @Override
    public void showError(@NonNull String errorTag, String message) {
        ((BaseActivity) mContext).showError(errorTag, message);
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
    }

    public void showProgressDialog() {
        if (getActivity() == null) return;
        if (!getActivity().isFinishing()) {
            mCustomProgressDialog.show(getActivity().getSupportFragmentManager(), "");
        }
    }

    public void hideProgressDialog() {
        mCustomProgressDialog.dismiss();
    }


}
