package com.qemasoft.alhabibshop.app.controller;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.model.MyCartDetail;

import java.util.List;


/**
 * Created by Inzimam on 17-Oct-17.
 */

public class CartDetailAdapter extends RecyclerView.Adapter<CartDetailAdapter.MyViewHolder> {

    private List<MyCartDetail> myCartDetailList;

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

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.e("OnBIndMethod", "OnBind Working");
        MyCartDetail data = myCartDetailList.get(position);
        holder.productName.setText(data.getProductName());
        EditText et = holder.qtyET;
//        holder.qtyET.setPrompt(data.getOrderQty());//setText(data.getOrderQty());

//        spinner.setSelection(getIndex(spinner, data.getOrderQty()));
//        int spinnerPosition = spinner.getAdapter().getPosition(compareValue);
        et.setText(data.getOrderQty());
        et.setSelection(et.length());
        holder.productPrice.setText(data.getProductPrice());
        int totalInt = Integer.parseInt(data.getOrderQty()) * Integer.parseInt(
                data.getProductPrice());
        holder.subTotal.setText("" + totalInt);
        int t = Utils.subTotalDummy += totalInt;
        Log.e("SubTotal = ", "" + t);
    }

    @Override
    public int getItemCount() {
        return myCartDetailList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView productName, orderQty, productPrice, subTotal;
        EditText qtyET;

        public MyViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            qtyET = itemView.findViewById(R.id.qty_spinner);
            productPrice = itemView.findViewById(R.id.unit_price);
            subTotal = itemView.findViewById(R.id.sub_total);

            Log.e("FindViewById", "Working");
        }
    }
    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }
}
