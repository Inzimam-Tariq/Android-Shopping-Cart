package com.qemasoft.alhabibshop.controller;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qemasoft.alhabibshop.model.MyOrder;

import java.util.List;

import hostflippa.com.opencart_android.R;

/**
 * Created by Inzimam on 17-Oct-17.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private List<MyOrder> myOrderList;

    public OrderAdapter(List<MyOrder> myOrderList) {
        this.myOrderList = myOrderList;
        Log.e("Constructor", "Working");
        Log.e("Constructor", "DataList Size = " + myOrderList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_order, parent, false);
        Log.e("LayoutInflated", "Working");

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.e("OnBIndMethod", "OnBind Working");
        MyOrder data = myOrderList.get(position);
//        holder.itemId.setText(data.getQuestionId());
        holder.orderId.setText(data.getOrderId());
        holder.orderStatus.setText(data.getOrderStatus());
        holder.orderQuantity.setText(data.getOrderQty());
        holder.orderDate.setText(data.getOrderDate());
        TextView tvPrice = holder.orderTotalPrice;
        tvPrice.setText(data.getOrderTotalPrice());
        // set StrikeThrough to textView
//        tvPrice.setPaintFlags(tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public int getItemCount() {
        return myOrderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView orderQuantity, orderId, orderTotalPrice, orderStatus, orderDate;
        public LinearLayout customLinearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            orderId =  itemView.findViewById(R.id.order_id_value);
            orderStatus =  itemView.findViewById(R.id.order_status);
            orderTotalPrice =  itemView.findViewById(R.id.order_total);
            orderQuantity =  itemView.findViewById(R.id.order_quantity);
            orderDate =  itemView.findViewById(R.id.order_date);
//            customLinearLayout = (LinearLayout) itemView.findViewById(R.id.custom_item_layout);
//            customLinearLayout.getLayoutParams().width = (int) (Utils.getScreenWidth(itemView.getContext()) / 2-4);
//            customLinearLayout.getLayoutParams().height = (int) (Utils.getScreenWidth(itemView.getContext()) / 2);
            Log.e("FindViewById", "Working");
        }
    }
}
