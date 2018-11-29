package com.gr8apes.weatherapp_takehomeexam.presentation.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;

import com.gr8apes.weatherapp_takehomeexam.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by LanarD on 22/11/2018.
 */
public class CustomProgressDialog extends CommonDialogFragment {

    private Unbinder mUnbinder;

    public static CustomProgressDialog build() {
        Bundle args = new Bundle();
        CustomProgressDialog fragment = new CustomProgressDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mUnbinder = ButterKnife.bind(this, view);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.custom_progress_dialog_layout;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getArguments() != null) {
            getArguments().clear();
        }
    }
}
