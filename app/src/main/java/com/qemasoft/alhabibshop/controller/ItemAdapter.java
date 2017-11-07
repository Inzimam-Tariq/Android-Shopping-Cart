package com.qemasoft.alhabibshop.controller;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qemasoft.alhabibshop.Utils;
import com.qemasoft.alhabibshop.model.MyItem;

import java.util.List;

import hostflippa.com.opencart_android.R;

/**
 * Created by Inzimam on 17-Oct-17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private List<MyItem> dataList;

    public ItemAdapter(List<MyItem> dataList) {
        this.dataList = dataList;
//        Log.e("Constructor", "Working");
//        Log.e("Constructor", "DataList Size = " + dataList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item, parent, false);
//        Log.e("LayoutInflated", "Working");

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        Log.e("OnBIndMethod", "OnBind Working");
        MyItem data = dataList.get(position);
//        holder.itemId.setText(data.getQuestionId());
        holder.itemTitle.setText(data.getItemTitle());
        holder.itemPriceDisc.setText(data.getItemPriceDisc());
        TextView tvPrice = holder.itemPriceFull;
        tvPrice.setText(data.getItemPriceFull());
        // set StrikeThrough to textView
        tvPrice.setPaintFlags(tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView itemId, itemTitle, itemPriceFull, itemPriceDisc;
        public LinearLayout customLinearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.item_title);
            itemPriceDisc = (TextView) itemView.findViewById(R.id.disc_price);
            itemPriceFull = (TextView) itemView.findViewById(R.id.full_price);
            customLinearLayout = (LinearLayout) itemView.findViewById(R.id.custom_item_layout);
            customLinearLayout.getLayoutParams().width = (int) (Utils.getScreenWidth(itemView.getContext()) / 2-4);
//            customLinearLayout.getLayoutParams().height = (int) (Utils.getScreenWidth(itemView.getContext()) / 2);
//            Log.e("FindViewById", "Working");
        }
    }
}
