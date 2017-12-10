package com.qemasoft.alhabibshop.app.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.model.MyCartDetail;
import com.qemasoft.alhabibshop.app.view.fragments.FragCartDetail;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Inzimam on 17-Oct-17.
 */

public class CartDetailAdapter extends RecyclerView.Adapter<CartDetailAdapter.MyViewHolder> {

    private List<MyCartDetail> myCartDetailList;
    private Context context;
    private Utils utils;
    private boolean isFromCheckout;

    public CartDetailAdapter(List<MyCartDetail> myCartDetailList, boolean isFromCheckout) {
        this.myCartDetailList = myCartDetailList;
        this.isFromCheckout = isFromCheckout;
        Log.e("Constructor", "Working");
        Log.e("Constructor", "DataList Size = " + myCartDetailList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutId;
        if (isFromCheckout) {
            layoutId = R.layout.layout_confirm_cart;
        } else {
            layoutId = R.layout.layout_cart;
        }

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);
        utils.printLog("LayoutInflated", "Working");

        this.context = parent.getContext();
        this.utils = new Utils(context);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        utils.printLog("OnBIndMethod", "OnBind Working");
        final MyCartDetail data = myCartDetailList.get(position);

        if (data != null){
            holder.productName.setText(data.getProductName());
            holder.productPrice.setText(data.getProductPrice());
            holder.total.setText(data.getTotal());
            if (isFromCheckout) {
                holder.productModel.setText(data.getProductModel());
                holder.productQty.setText(data.getOrderQty());
            } else {
                String imgPath = data.getProductImage();
                if (!imgPath.isEmpty()) {
                    Picasso.with(context).load(imgPath).into(holder.productImage);
                }

                EditText et = holder.qtyET;
                et.setText(data.getOrderQty());
                et.setSelection(et.length());

                holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", data.getCartId());
                        bundle.putString("self", "");
                        utils.switchFragment(new FragCartDetail(), bundle);
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return myCartDetailList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productModel, productQty, productPrice, total;
        EditText qtyET;
        ImageView productImage, updateIcon, deleteIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price_val_tv);
            total = itemView.findViewById(R.id.total_val_tv);
            if (isFromCheckout) {
                productModel = itemView.findViewById(R.id.product_model_val_tv);
                productQty = itemView.findViewById(R.id.product_qty_val_tv);
            } else {
                productImage = itemView.findViewById(R.id.product_img);
                qtyET = itemView.findViewById(R.id.qty_spinner);
                updateIcon = itemView.findViewById(R.id.update_cart_icon);
                deleteIcon = itemView.findViewById(R.id.remove_cart_icon);
            }

            utils.printLog("FindViewById", "Working");
        }
    }
}
