package com.gr8apes.weatherapp_takehomeexam.presentation.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.gr8apes.weatherapp_takehomeexam.R;
import com.gr8apes.weatherapp_takehomeexam.presentation.contract.LoadingView;
import com.gr8apes.weatherapp_takehomeexam.presentation.dialog.CustomProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import de.mateware.snacky.Snacky;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public abstract class BaseActivity extends AppCompatActivity implements LoadingView {

    public static final int REQUEST_CODE_PERMISSION_LOCATION = 101;
   public static final String NEVERASKAGAIN = "NEVERASKAGAIN";

    protected static final int FILE_SELECT_CODE = 0;

    Unbinder unbinder;

    protected Context mContext;
    protected Toolbar mToolbar;
    protected AlertDialog mAlertDialog;
    protected CustomProgressDialog mCustomProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResourceId());

        mContext = this;

        AndroidInjection.inject(this);

        unbinder = ButterKnife.bind(this);

        initToolBar();

        mCustomProgressDialog = CustomProgressDialog.build();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    private void initToolBar() {
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    public void setToolbar(int color, String toolbarTitle) {
        if (getSupportActionBar() == null || mToolbar == null) return;

        TextView toolbarTitleTextView = mToolbar.findViewById(R.id.toolbar_title);
        toolbarTitleTextView.setText(toolbarTitle);

        mToolbar.setBackgroundColor(ContextCompat.getColor(mContext, color));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }

    public void hideToolBar() {
        if (mToolbar != null) {
            mToolbar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    protected abstract int setLayoutResourceId();

    @Override
    public void showError(@NonNull String errorTag, String message) {
        showSnackBarError(message, "Okay", null); //todo temporay | replace with the right dialog
    }

    public void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    public void replaceFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected void checkForPermissions(int requestCode, String... permissions) {
        List<String> permissionsNeeded = new ArrayList<>();

        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(permission);
            }
        }

        if (permissionsNeeded.size() > 0) {
            ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[permissionsNeeded.size()]), requestCode);
            return;
        }

        permissionGranted(requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); //to call onRequestPermissionsResult on activities fragment

        if (isNeverClicked(permissions)) {
            Log.i("", "onRequestPermissionsResult: is never");
            permissionDenied(NEVERASKAGAIN);
        }

        if (grantResults.length > 1) { // Handle multiple permission request
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    permissionDenied("Some Permission is Denied");
                    return;
                }
            }
            permissionGranted(requestCode);
        } else {
            // Handle single permission request
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionGranted(requestCode);
            } else {
                permissionDenied("Permission Denied");
            }
        }
    }

    private boolean isNeverClicked(@NonNull String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                //denied
            } else {
                if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                    //allowed
                } else {
                    Log.e("set to never ask again", permission); //set to never ask again
                    return true;
                }
            }
        }
        return false;
    }

    protected void permissionGranted(int requestCode) {
    }

    protected void permissionDenied(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }


    public void showSnackBarSuccess(String message, String actiontext, @Nullable View.OnClickListener onClickListener) {
        Snacky.builder().setActivity(this)
                .setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                .setText(message)
                .setActionText(actiontext)
                .setActionClickListener(onClickListener)
                .setDuration(Snacky.LENGTH_SHORT)
                .success()
                .show();
    }

    public void showSnackBarError(String message, String actiontext, @Nullable View.OnClickListener onClickListener) {
        Snacky.builder().setActivity(this)
                .setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorTitleBlack))
                .setText(message)
                .setActionText(actiontext)
                .setActionClickListener(onClickListener)
                .setDuration(Snacky.LENGTH_SHORT)
                .error()
                .show();
    }

    public void showSnackBarWarning(String message, String actiontext, @Nullable View.OnClickListener onClickListener) {
        Snacky.builder().setActivity(this)
                .setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorLightOrange))
                .setText(message)
                .setActionText(actiontext)
                .setActionClickListener(onClickListener)
                .setDuration(Snacky.LENGTH_SHORT)
                .warning()
                .show();
    }

    public void showDialog(String title, String message, String negativeButtonTitle, String positiveButtonTitle, @Nullable DialogInterface.OnClickListener onClickNegative, @Nullable DialogInterface.OnClickListener onClickPositive) {
        if (mAlertDialog != null && mAlertDialog.isShowing()) return;
        mAlertDialog = new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(Html.fromHtml(message))
                .setCancelable(false)
                .setPositiveButton(positiveButtonTitle, onClickPositive)
                .setNegativeButton(negativeButtonTitle, onClickNegative).create();
        mAlertDialog.show();
    }

    public void showDialog(String title, String message, String positiveButtonTitle, @Nullable DialogInterface.OnClickListener onClickPositive) {
        if (mAlertDialog != null && mAlertDialog.isShowing()) return;
        mAlertDialog = new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(Html.fromHtml(message))
                .setCancelable(false)
                .setPositiveButton(positiveButtonTitle, onClickPositive)
                .create();
        mAlertDialog.show();
    }

    public void showProgressDialog() {
        if (!this.isFinishing()) {
            mCustomProgressDialog.show(getSupportFragmentManager(), "");
        }
    }

    public void hideProgressDialog() {
        mCustomProgressDialog.dismiss();
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
    }
}
