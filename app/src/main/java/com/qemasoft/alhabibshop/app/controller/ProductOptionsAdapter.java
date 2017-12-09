package com.qemasoft.alhabibshop.app.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.model.Options;
import com.qemasoft.alhabibshop.app.model.ProductOptionValueItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Inzimam on 17-Oct-17.
 */

public class ProductOptionsAdapter extends RecyclerView.Adapter<ProductOptionsAdapter.MyViewHolder> {

    private List<Options> dataList;
    private Context context;
    private Utils utils;

    public ProductOptionsAdapter(List<Options> dataList) {
        this.dataList = dataList;
        Log.e("Constructor", "Working");
        Log.e("Constructor", "DataList Size = " + dataList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_product_option, parent, false);
        Log.e("LayoutInflated", "Working");
        this.context = parent.getContext();
        this.utils = new Utils(context);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.e("OnBIndMethod", "OnBind Working");
//        final int pos = holder.getAdapterPosition();
        final Options data = dataList.get(position);

        holder.optionsNameTV.setText(data.getName());
        final List<String> options = new ArrayList<>();

        for (int i = 0; i < data.getProductOptionValueItemList().size(); i++) {
            ProductOptionValueItem item = data.getProductOptionValueItemList().get(i);
            Log.e("Option", " option name = "+ item.getName());
            options.add(item.getName());
        }

        final Button textView = holder.optionsTV;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.showRadioAlertDialog(textView,  data.getName(), options, 0);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView optionsNameTV;
                Button optionsTV;


        public MyViewHolder(View itemView) {
            super(itemView);
            optionsTV = itemView.findViewById(R.id.options_tv);
            optionsNameTV = itemView.findViewById(R.id.option_name_tv);

            Log.e("FindViewById", "Working");
        }
    }

}
