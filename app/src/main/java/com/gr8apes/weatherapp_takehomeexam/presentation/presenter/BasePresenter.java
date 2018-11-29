package com.gr8apes.weatherapp_takehomeexam.presentation.presenter;

import android.util.Log;


import com.gr8apes.weatherapp_takehomeexam.data.rest.error.ErrorResponse;
import com.gr8apes.weatherapp_takehomeexam.data.rest.error.NoConnectivityException;
import com.gr8apes.weatherapp_takehomeexam.data.rest.error.RestErrorHandler;
import com.gr8apes.weatherapp_takehomeexam.presentation.contract.BaseView;

import java.io.IOException;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by LanarD on 18/09/2018.
 */

public class BasePresenter<T extends BaseView> {

    public static final String TAG = BasePresenter.class.getSimpleName();
    public static final String ERROR_PARSE = "Error parsing data";
    public static final String ERROR_GENERIC = "Oops… Something wrong happened.";
    public static final String ERROR_CONNECTION = "Oops… There's something wrong with your connection.";

    protected T view;


    private final CompositeDisposable subscriptionsToUnsubscribeOnUnbindView = new CompositeDisposable();

    protected final void unsubscribeOnUnbindView(@NonNull Disposable disposable) {
        subscriptionsToUnsubscribeOnUnbindView.add(disposable);
    }

    public void bindView(T view) {
        this.view = view;
    }

    public void unBindView() {
        this.view = null;
        subscriptionsToUnsubscribeOnUnbindView.clear();
    }

    public boolean isViewBound() {
        return view != null;
    }

    protected ErrorResponse getErrorResponse(Throwable e) {
        Log.e(TAG, "getErrorResponse: ", e);
        if (e instanceof HttpException) {
            if (((HttpException) e).code() == 500) {
                return new ErrorResponse(-5, ERROR_GENERIC);
            }
            try {
                HttpException error = (HttpException) e;
                String errorBody = error.response().errorBody().string();
                return RestErrorHandler.parseErrorDetails(((HttpException) e).code(), errorBody);
            } catch (IOException e1) {
                Log.e(TAG, e1.getMessage(), e1);
                return new ErrorResponse(0, ERROR_PARSE);
            }
        } else if (e instanceof NoConnectivityException) {
            return new ErrorResponse(-1, ERROR_CONNECTION);
        }
        return new ErrorResponse(-5, ERROR_GENERIC);
    }

}
