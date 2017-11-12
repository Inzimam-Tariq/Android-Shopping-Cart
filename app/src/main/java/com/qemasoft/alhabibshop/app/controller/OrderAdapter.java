package com.qemasoft.alhabibshop.app.controller;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
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
        this.context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Log.e("OnBIndMethod", "OnBind Working");
        MyOrder data = myOrderList.get(position);
//        holder.itemId.setText(data.getQuestionId());
        holder.orderId.setText(data.getOrderId());
        holder.orderStatus.setText(data.getOrderStatus());
        holder.orderQuantity.setText(data.getOrderQty());
        holder.orderDate.setText(data.getOrderDate());
        TextView tvPrice = holder.orderTotalPrice;
        tvPrice.setText(data.getOrderTotalPrice());
//        double width = holder.customLinearLayout.getWidth();
//        Utils utils = new Utils(context);
//        utils.showToast("Clicked & Position is : " + position);
        holder.viewOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils utils = new Utils(context);
                utils.showToast("Clicked & Position is : "+position);
//                MainActivity.changeFragment(0);
                FragOrderDetail frag = new FragOrderDetail();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.flFragments,frag).commit();
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

        public TextView orderQuantity, orderId, orderTotalPrice, orderStatus, orderDate;
        public LinearLayout customLinearLayout;
        Button viewOrderBtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.order_id_value);
            orderStatus = itemView.findViewById(R.id.order_status);
            orderTotalPrice = itemView.findViewById(R.id.order_total);
            orderQuantity = itemView.findViewById(R.id.order_quantity);
            orderDate = itemView.findViewById(R.id.order_date);
            viewOrderBtn = itemView.findViewById(R.id.view_order_btn);
//            customLinearLayout = (LinearLayout) itemView.findViewById(R.id.custom_item_layout);

//            double width = viewOrderBtn.getWidth();
//            Utils utils = new Utils(context);
//            utils.showToast("Clicked & Position is : " + width);

//            customLinearLayout.getLayoutParams().width = (int) (Utils.getScreenWidth(itemView.getContext()) / 2-4);
//            customLinearLayout.getLayoutParams().height = (int) (Utils.getScreenWidth(itemView.getContext()) / 2);
            Log.e("FindViewById", "Working");
        }
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
//            int itemPosition = recyclerView.indexOfChild(v);
//            Log.e("Clicked and Position is",String.valueOf(itemPosition));
        }
    }
}
