package com.qemasoft.alhabibshop.app.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.model.Product;

import java.util.List;


/**
 * Created by Inzimam on 17-Oct-17.
 */

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder> {

    private List<Product> productList;
    private Context context;
    private Utils utils;

    public OrderDetailAdapter(List<Product> myOrderDetailList) {
        this.productList = myOrderDetailList;
        Log.e("Constructor", "Working");
        Log.e("Constructor", "DataList Size = " + myOrderDetailList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        this.utils = new Utils(context);
        
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_order_detail, parent, false);

        utils.printLog("LayoutInflated", "Working");

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        utils.printLog("OnBIndMethod", "OnBind Working");
        Product data = productList.get(position);
        holder.productName.setText(data.getName());
        holder.orderQty.setText(data.getQuantity());
        holder.productPrice.setText(data.getPrice());
        holder.total.setText(data.getTotal());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView productName, orderQty, productPrice, total;

        public MyViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name_val_tv);
            orderQty = itemView.findViewById(R.id.order_quantity);
            productPrice = itemView.findViewById(R.id.product_price);
            total = itemView.findViewById(R.id.total);

            utils.printLog("FindViewById", "Working");
        }
    }
}
