package com.gr8apes.weatherapp_takehomeexam.presentation.dialog.option;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gr8apes.weatherapp_takehomeexam.R;

import java.util.ArrayList;

/**
 * Created by LanarD on 06/10/2018.
 */

public class OptionDialogRecyclerviewAdapter extends RecyclerView.Adapter<OptionDialogRecyclerviewAdapter.MyHolder> {

    Context mContext;
    ArrayList<Option> mOptions;
    OptionDialog.OptionDialogView mMyOptionDialogView;

    public OptionDialogRecyclerviewAdapter(Context context, ArrayList<Option> options, OptionDialog.OptionDialogView myOptionDialogView) {
        this.mContext = context;
        this.mOptions = options;
        this.mMyOptionDialogView = myOptionDialogView;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_dialog_item, parent, false);
        MyHolder holder = new MyHolder(v);
        return holder;
    }

    //BIND DATA
    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        Log.i(OptionDialogRecyclerviewAdapter.class.getSimpleName(), "onBindViewHolder: " + mOptions.size());
        holder.option_name.setText(mOptions.get(position).getOptionName());
        holder.option_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyOptionDialogView.onClickOption(mOptions.get(position).getOptionId());
            }
        });
    }


    @Override
    public int getItemCount() {
        return mOptions.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView option_name;

        public MyHolder(View itemView) {
            super(itemView);
            option_name = (TextView) itemView.findViewById(R.id.option_name);
        }
    }
}
