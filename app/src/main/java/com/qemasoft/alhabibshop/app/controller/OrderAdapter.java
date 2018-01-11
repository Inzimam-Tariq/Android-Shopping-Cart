package com.qemasoft.alhabibshop.app.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.model.MyOrder;
import com.qemasoft.alhabibshop.app.view.fragments.FragOrderDetail;

import java.util.List;

/**
 * Created by Inzimam on 17-Oct-17.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private List<MyOrder> myOrderList;
    private Context context;
    private Utils utils;

    public OrderAdapter(List<MyOrder> myOrderList) {
        this.myOrderList = myOrderList;
        Log.e("Constructor", "Working");
        Log.e("Constructor", "DataList Size = " + myOrderList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        this.utils = new Utils(context);

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_order, parent, false);

        utils.printLog("LayoutInflated", "Working");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        utils.printLog("OnBIndMethod", "OnBind Working");
        final MyOrder data = myOrderList.get(holder.getAdapterPosition());

        String orderId = data.getOrderId();
        holder.orderId.setText(orderId);
        holder.orderStatus.setText(data.getOrderStatus());
        holder.orderQuantity.setText(data.getOrderQty());
        holder.orderDate.setText(data.getOrderDate());
        TextView tvPrice = holder.orderTotalPrice;
        tvPrice.setText(data.getOrderTotalPrice());

        holder.viewOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", data.getOrderId());
//                utils.printLog("Clicked on Position : " + holder.getAdapterPosition());

                utils.switchFragment(new FragOrderDetail(), bundle);
            }
        });
        // set StrikeThrough to textView
//        tvPrice.setPaintFlags(tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public int getItemCount() {
        return myOrderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView orderQuantity, orderId, orderTotalPrice, orderStatus, orderDate;
        private LinearLayout customLinearLayout;
        private Button viewOrderBtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.order_id_value);
            orderStatus = itemView.findViewById(R.id.order_status);
            orderTotalPrice = itemView.findViewById(R.id.order_total);
            orderQuantity = itemView.findViewById(R.id.order_quantity);
            orderDate = itemView.findViewById(R.id.order_date);
            viewOrderBtn = itemView.findViewById(R.id.view_order_btn);

            utils.printLog("FindViewById", "Working");
        }
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
//            int itemPosition = recyclerView.indexOfChild(v);
//            utils.printLog("Clicked and Position is",String.valueOf(itemPosition));
        }
    }
}
