package com.gr8apes.weatherapp_takehomeexam.presentation.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.widget.Toast;

import com.gr8apes.weatherapp_takehomeexam.R;
import com.gr8apes.weatherapp_takehomeexam.presentation.event.Event;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LanarD on 29/11/2018.
 */
public class RefreshButtonContainerFragment extends BaseFragment {

    public static RefreshButtonContainerFragment newInstance() {
        Bundle args = new Bundle();
        RefreshButtonContainerFragment fragment = new RefreshButtonContainerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_refresh_button;
    }

    @OnClick(R.id.refreshButton)
    void onClickRefresh() {
        EventBus.getDefault().post(new Event.Refresh(true));
    }
}
