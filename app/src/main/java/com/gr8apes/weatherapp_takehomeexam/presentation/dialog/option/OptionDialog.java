package com.gr8apes.weatherapp_takehomeexam.presentation.dialog.option;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.gr8apes.weatherapp_takehomeexam.R;
import com.gr8apes.weatherapp_takehomeexam.presentation.dialog.CommonDialogFragment;
import com.gr8apes.weatherapp_takehomeexam.presentation.utility.GeneralUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OptionDialog extends CommonDialogFragment {

    public static String MYOPTION = "MYOPTION";
    public static String MYOPTION_LABEL = "MYOPTION_LABEL";
    public static String MYOPTION_VIEW = "MYOPTION_VIEW";

    @BindView(R.id.label)
    TextView mLabel;

    @BindView(R.id.myOptionsRecyclerView)
    RecyclerView mMyOptionsRecyclerView;

    private ArrayList<Option> mOptions;
    private OptionDialogView mMyOptionDialogView;
    private String mLabelString;
    private Unbinder mUnbinder;

    public interface OptionDialogView extends Serializable {
        void onClickOption(int optionID);
    }


    public static OptionDialog build(String label, List<Option> options, OptionDialogView myOptionDialogView) {
        Bundle args = new Bundle();
        args.putString(MYOPTION_LABEL, label);
        args.putSerializable(MYOPTION, (Serializable) options);
        args.putSerializable(MYOPTION_VIEW, myOptionDialogView);
        OptionDialog fragment = new OptionDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (null != arguments) {
            this.mOptions = (ArrayList<Option>) arguments.getSerializable(MYOPTION);
            this.mMyOptionDialogView = (OptionDialogView) arguments.getSerializable(MYOPTION_VIEW);
            this.mLabelString = getArguments().getString(MYOPTION_LABEL);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_option_dialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setPositiveButtonId(R.id.dialog_positive);
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mUnbinder = ButterKnife.bind(this, view);

        mLabel.setText(mLabelString);

        OptionDialogRecyclerviewAdapter optionDialogRecyclerviewAdapter = new OptionDialogRecyclerviewAdapter(getActivity(), mOptions, mMyOptionDialogView);
        GeneralUtils.setDefaultRecyclerView(getActivity(), mMyOptionsRecyclerView, optionDialogRecyclerviewAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

