package com.qemasoft.alhabibshop.app.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.model.MyCartDetail;
import com.qemasoft.alhabibshop.app.view.activities.MainActivity;
import com.qemasoft.alhabibshop.app.view.fragments.FragCartDetail;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Inzimam on 17-Oct-17.
 */

public class CartDetailAdapter extends RecyclerView.Adapter<CartDetailAdapter.MyViewHolder> {

    private List<MyCartDetail> myCartDetailList;
    private Context context;

    public CartDetailAdapter(List<MyCartDetail> myCartDetailList) {
        this.myCartDetailList = myCartDetailList;
        Log.e("Constructor", "Working");
        Log.e("Constructor", "DataList Size = " + myCartDetailList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cart, parent, false);
        Log.e("LayoutInflated", "Working");

        this.context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.e("OnBIndMethod", "OnBind Working");
        final MyCartDetail data = myCartDetailList.get(position);

        Picasso.with(context).load(data.getProductImage()).into(holder.productImage);
        holder.productName.setText(data.getProductName());
        EditText et = holder.qtyET;
        et.setText(data.getOrderQty());
        et.setSelection(et.length());
        holder.productPrice.setText(data.getProductPrice());
        holder.subTotal.setText(data.getTotal());

        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", data.getCartId());
                bundle.putString("self", "");
                Fragment fragment = new FragCartDetail();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.flFragments, fragment).commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return myCartDetailList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productPrice, subTotal;
        EditText qtyET;
        ImageView productImage, updateIcon, deleteIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_img);
            productName = itemView.findViewById(R.id.product_name);
            qtyET = itemView.findViewById(R.id.qty_spinner);
            productPrice = itemView.findViewById(R.id.unit_price);
            subTotal = itemView.findViewById(R.id.sub_total_val_tv);

            updateIcon = itemView.findViewById(R.id.update_cart_icon);
            deleteIcon = itemView.findViewById(R.id.remove_cart_icon);

            Log.e("FindViewById", "Working");
        }
    }
}
