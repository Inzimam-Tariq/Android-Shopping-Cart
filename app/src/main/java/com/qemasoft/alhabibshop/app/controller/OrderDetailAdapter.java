package com.qemasoft.alhabibshop.app.controller;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.model.MyOrderDetail;

import java.util.List;


/**
 * Created by Inzimam on 17-Oct-17.
 */

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder> {

    private List<MyOrderDetail> myOrderDetailList;

    public OrderDetailAdapter(List<MyOrderDetail> myOrderDetailList) {
        this.myOrderDetailList = myOrderDetailList;
        Log.e("Constructor", "Working");
        Log.e("Constructor", "DataList Size = " + myOrderDetailList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_order_detail, parent, false);
        Log.e("LayoutInflated", "Working");

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.e("OnBIndMethod", "OnBind Working");
        MyOrderDetail data = myOrderDetailList.get(position);
        holder.productName.setText(data.getProductName());
        holder.orderQty.setText(data.getOrderQty());
        holder.productPrice.setText(data.getProductPrice());
        int totalInt = Integer.parseInt(data.getOrderQty()) * Integer.parseInt(
                data.getProductPrice());
        holder.total.setText("" + totalInt);
        int t = Utils.subTotalDummy += totalInt;
        Log.e("SubTotal = ", "" + t);
    }

    @Override
    public int getItemCount() {
        return myOrderDetailList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView productName, orderQty, productPrice, total;

        public MyViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            orderQty = itemView.findViewById(R.id.order_quantity);
            productPrice = itemView.findViewById(R.id.product_price);
            total = itemView.findViewById(R.id.total);

            Log.e("FindViewById", "Working");
        }
    }
}
