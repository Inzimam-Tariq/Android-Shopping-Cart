package com.qemasoft.alhabibshop.app.controller;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.model.MyItem;
import com.qemasoft.alhabibshop.app.view.fragments.FragProductDetail;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Inzimam on 17-Oct-17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private List<MyItem> dataList;
    private Context context;
    private Utils utils;

    public ItemAdapter(List<MyItem> dataList) {
        this.dataList = dataList;
//        Log.e("Constructor", "Working");
//        Log.e("Constructor", "DataList Size = " + dataList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item, parent, false);
//        utils.printLog("LayoutInflated", "Working");
        this.context = parent.getContext();
        this.utils = new Utils(context);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
//        utils.printLog("OnBIndMethod", "OnBind Working");
        final MyItem data = dataList.get(position);

        if (data != null){
            holder.itemTitle.setText(data.getItemTitle());
            Picasso.with(context).load(data.getItemImage()).into(holder.img);
            holder.itemPriceFull.setText(data.getItemPriceFull());

            TextView tvPrice = holder.itemPriceDisc;
            // set StrikeThrough to textView
            if (!data.getItemPriceDisc().isEmpty()) {
                tvPrice.setVisibility(View.VISIBLE);
                tvPrice.setText(data.getItemPriceDisc());
                tvPrice.setPaintFlags(tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", data.getItemId());
                    utils.printLog("itemId", data.getItemId());

                    utils.switchFragment(new FragProductDetail(), bundle);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView itemTitle, itemPriceFull, itemPriceDisc;
        public LinearLayout customLinearLayout;
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemPriceDisc = itemView.findViewById(R.id.disc_price);
            itemPriceFull = itemView.findViewById(R.id.full_price);
            img = itemView.findViewById(R.id.img);
            customLinearLayout = itemView.findViewById(R.id.custom_item_layout);
            customLinearLayout.getLayoutParams().width = Utils.getScreenWidth(
                    itemView.getContext()) / 2 - 5;
        }
    }
}
