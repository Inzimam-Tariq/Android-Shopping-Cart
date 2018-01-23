package com.qemasoft.alhabibshop.app.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.Preferences;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.model.MyCartDetail;
import com.qemasoft.alhabibshop.app.model.Options;
import com.qemasoft.alhabibshop.app.view.activities.MainActivity;
import com.qemasoft.alhabibshop.app.view.fragments.FragCartDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.qemasoft.alhabibshop.app.AppConstants.ITEM_COUNTER;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;


/**
 * Created by Inzimam on 17-Oct-17.
 */

public class CartDetailAdapter
        extends RecyclerView.Adapter<CartDetailAdapter.MyViewHolder> {

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

        this.context = parent.getContext();
        this.utils = new Utils(context);

        int layoutId;
        if (isFromCheckout) {
            layoutId = R.layout.layout_confirm_cart;
        } else {
            layoutId = R.layout.layout_cart_new;
        }

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);

        utils.printLog("LayoutInflated", "Working");

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        utils.printLog("OnBIndMethod", "OnBind Working");
        final MyCartDetail data = myCartDetailList.get(position);

        if (data != null) {
            holder.productTitle.setText(data.getProductName());

            List<Options> optionsList = data.getOptionsList();
            utils.printLog("OptionListInAdapt = " + optionsList);
//            if (optionsList != null && optionsList.size() > 0) {
                List<TextView> textViewList = new ArrayList<>(3);
                for (int i = 0; i < 5; i++) {
                    TextView textView = new TextView(context);
                    textView.setLayoutParams(
                            new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                    textView.setText("\u25CF TextView" + i);
                    holder.productOptionsLayout.addView(textView);
                }

//            }


            String symbol = Preferences.getSharedPreferenceString(context
                    , AppConstants.CURRENCY_SYMBOL_KEY, "$");
            String p = data.getOrderQty().concat("x")
                    .concat(data.getProductPrice().concat("").concat(symbol));
            holder.productPrice.setText(p);
//            holder.total.setText(data.getTotal().concat("").concat(symbol));
            if (isFromCheckout) {
                holder.productModel.setText(data.getProductModel());
                holder.productQty.setText(data.getOrderQty().concat("x"));
            } else {
                String imgPath = data.getProductImage();
                utils.printLog("Product Image = " + imgPath);
                if (!imgPath.isEmpty()) {
                    Picasso.with(context).load(imgPath).into(holder.productImage);
                }

                final TextView qtyTV = holder.productQty;
                qtyTV.setText(data.getOrderQty());

                holder.deleteIcon.setOnClickListener(
                        new MyOnClickListener(holder, data.getCartId()));
                holder.updateIconPlus.setOnClickListener(
                        new MyOnClickListener(holder, data.getCartId()));
                holder.updateIconMinus.setOnClickListener(
                        new MyOnClickListener(holder, data.getCartId()));
            }
        }

    }

    @Override
    public int getItemCount() {
        return myCartDetailList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout productOptionsLayout;
        TextView productTitle, productModel, productQty, productPrice, total;
        ImageView productImage;
        ImageButton updateIconPlus, updateIconMinus;
        ImageView deleteIcon;

        public MyViewHolder(View itemView) {
            super(itemView);

            productOptionsLayout = itemView.findViewById(R.id.product_options_layout);

            productTitle = itemView.findViewById(R.id.product_title_tv);
            productPrice = itemView.findViewById(R.id.product_price_tv);
            total = itemView.findViewById(R.id.total_val_tv);
            if (isFromCheckout) {
                productModel = itemView.findViewById(R.id.product_model_val_tv);
                productQty = itemView.findViewById(R.id.product_qty_val_tv);
            } else {
                productImage = itemView.findViewById(R.id.product_img);
                updateIconPlus = itemView.findViewById(R.id.update_cart_ibtn_plus);
                productQty = itemView.findViewById(R.id.product_qty_tv);
                updateIconMinus = itemView.findViewById(R.id.update_cart_ibtn_minus);
                deleteIcon = itemView.findViewById(R.id.remove_cart_iv);
            }

            utils.printLog("FindViewById", "Working");
        }
    }

    private class MyOnClickListener implements View.OnClickListener {

        private String cartId;
        private MyViewHolder holder;

        MyOnClickListener(MyViewHolder holder, String cartId) {
            this.holder = holder;
            this.cartId = cartId;
        }

        @Override
        public void onClick(View v) {
            final Bundle bundle = new Bundle();
            int id = v.getId();
            TextView qtyTV = holder.productQty;
            if (id == R.id.remove_cart_iv) {
                bundle.putString("id", cartId);
                bundle.putString("midFix", "removeCart");
                utils.switchFragment(new FragCartDetail(), bundle);
                int val = Preferences.getSharedPreferenceInt(appContext, ITEM_COUNTER, 0);

                ((MainActivity) context).counterTV.setText(String.valueOf(val - 1));
            } else {
                int quantity = Integer.parseInt(qtyTV.getText().toString());
                if (id == R.id.update_cart_ibtn_plus) {
                    ++quantity;
                } else if (id == R.id.update_cart_ibtn_minus) {
                    if (quantity <= 1) {
                        utils.showAlertDialog("Information",
                                "Quantity Can't be 0 or Negative");
                        return;
                    } else {
                        --quantity;
                    }
                }
                utils.printLog("Quantity = " + quantity);
                bundle.putString("id", cartId);
                bundle.putString("midFix", "updateCart");
                bundle.putString("qty", "" + quantity);
                utils.switchFragment(new FragCartDetail(), bundle);


            }

        }
    }
}
