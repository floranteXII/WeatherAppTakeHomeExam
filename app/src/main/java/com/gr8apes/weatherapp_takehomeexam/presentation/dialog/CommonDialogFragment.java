package com.gr8apes.weatherapp_takehomeexam.presentation.dialog;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class CommonDialogFragment extends DialogFragment implements View.OnClickListener {
    private static final String TAG = CommonDialogFragment.class.getSimpleName();
    public static final int NEUTRAL = -1;
    public static final int NEGATIVE = 0;
    public static final int POSITIVE = 1;
    public static final int PENDING = 2;
    public static final int GIFT = 3;
    private int negativeButtonId;
    private int neutralButtonId;
    private int positiveButtonId;

    private CommonDialogListener listener = new CommonDialogListener() {
        @Override
        public void onPositiveButtonClicked() {

        }

        @Override
        public void onNegativeButtonClicked() {

        }

        @Override
        public void onNeutralButtonClicked() {

        }
    };

    private PositiveButtonListener positiveButtonListener = listener;
    private NegativeButtonListener negativeButtonListener = listener;
    private NeutralButtonListener neutralButtonListener = listener;

    protected int getPositiveButtonId() {
        return positiveButtonId;
    }

    protected int getNegativeButtonId() {
        return negativeButtonId;
    }

    protected int getNeutralButtonId() {
        return neutralButtonId;
    }

    protected void setNegativeButtonId(int negativeButtonId) {
        this.negativeButtonId = negativeButtonId;
    }

    protected void setNeutralButtonId(int neutralButtonId) {
        this.neutralButtonId = neutralButtonId;
    }

    protected void setPositiveButtonId(int positiveButtonId) {
        this.positiveButtonId = positiveButtonId;
    }

    protected abstract int getLayoutId();

    public interface CommonDialogListener extends PositiveButtonListener, NegativeButtonListener, NeutralButtonListener {
    }

    public interface PositiveButtonListener {
        void onPositiveButtonClicked();
    }

    public interface NegativeButtonListener {
        void onNegativeButtonClicked();
    }

    public interface NeutralButtonListener {
        void onNeutralButtonClicked();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View positive = view.findViewById(getPositiveButtonId());
        View negative = view.findViewById(getNegativeButtonId());
        View neutral = view.findViewById(getNeutralButtonId());

        if (null != positive) {
            positive.setOnClickListener(this);
        }

        if (null != negative) {
            negative.setOnClickListener(this);
        }

        if (null != neutral) {
            neutral.setOnClickListener(this);
        }
    }

    public void setListener(CommonDialogListener listener) {
        this.positiveButtonListener = listener;
        this.negativeButtonListener = listener;
        this.neutralButtonListener = listener;
    }

    public void setPositiveButtonListener(PositiveButtonListener listener) {
        this.positiveButtonListener = listener;
    }

    public void setNegativeButtonListener(NegativeButtonListener listener) {
        this.negativeButtonListener = listener;
    }

    public void setNeutralButtonListener(NeutralButtonListener listener) {
        this.neutralButtonListener = listener;
    }

    protected void onClickPositive() {
        positiveButtonListener.onPositiveButtonClicked();
        dismiss();
    }

    protected void onClickNegative() {
        negativeButtonListener.onNegativeButtonClicked();
        dismiss();
    }

    protected void onClickNeutral() {
        neutralButtonListener.onNeutralButtonClicked();
        dismiss();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == getPositiveButtonId()) {
            onClickPositive();
        } else if (v.getId() == getNegativeButtonId()) {
            onClickNegative();
        } else if (v.getId() == getNeutralButtonId()) {
            onClickNeutral();
        }
    }

}