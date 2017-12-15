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
    private ProductOptionsAdapterInterface adapterInterface;

    public ProductOptionsAdapter(List<Options> dataList,
                                 ProductOptionsAdapterInterface
                                         adapterInterface) {
        this.dataList = dataList;
        this.adapterInterface = adapterInterface;
        Log.e("Constructor", "Working");
        Log.e("Constructor", "DataList Size = " + dataList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        this.utils = new Utils(context);

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_product_option, parent, false);

        utils.printLog("LayoutInflated");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        utils.printLog("OnBind Working");

        final Options data = dataList.get(position);

        if (data != null) {
            holder.optionsNameTV.setText(data.getName());
            final List<String> strOptions = new ArrayList<>();
            final List<ProductOptionValueItem> optionsList = new ArrayList<>();

            for (int i = 0; i < data.getProductOptionValueItemList().size(); i++) {
                ProductOptionValueItem item = data.getProductOptionValueItemList().get(i);
                utils.printLog("Option", " option name = " + item.getName());
                strOptions.add(item.getName());
                optionsList.add(item);
            }

            final Button btn = holder.optionsBtn;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.ClickInterface clickInterface
                            = new Utils.ClickInterface() {
                        @Override
                        public void OnItemClicked(int position) {
                            utils.printLog("Interface Bridge Working! Clicked at = " + position +
                                    "\nAdapter Position is = " + holder.getAdapterPosition());
                            String val = String.valueOf(optionsList.get(position).getOptionValueId());
                            adapterInterface.OnItemClicked(holder.getAdapterPosition(), val);
                        }
                    };
                    utils.showRadioAlertDialog(btn, data.getName()
                            , strOptions, 0, clickInterface);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface ProductOptionsAdapterInterface {
        void OnItemClicked(int adapterPosition, String value);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView optionsNameTV;
        Button optionsBtn;


        public MyViewHolder(View itemView) {
            super(itemView);
            optionsBtn = itemView.findViewById(R.id.options_btn);
            optionsNameTV = itemView.findViewById(R.id.option_name_tv);

            utils.printLog("FindViewById Working");
        }
    }

}
