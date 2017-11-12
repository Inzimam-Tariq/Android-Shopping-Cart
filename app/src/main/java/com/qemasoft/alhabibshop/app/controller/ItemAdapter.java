package com.qemasoft.alhabibshop.app.controller;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
        this.context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
//        Log.e("OnBIndMethod", "OnBind Working");
        MyItem data = dataList.get(position);
//        holder.itemId.setText(data.getQuestionId());
        holder.itemTitle.setText(data.getItemTitle());
        Picasso.with(context).load(data.getItemImage()).into(holder.img);
        holder.itemPriceFull.setText(data.getItemPriceFull());

        TextView tvPrice = holder.itemPriceDisc;
        tvPrice.setText(data.getItemPriceDisc());
        // set StrikeThrough to textView
        tvPrice.setPaintFlags(tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            AppCompatActivity activity = (AppCompatActivity) context;

            @Override
            public void onClick(View v) {
                Fragment fragment = new FragProductDetail();
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.flFragments, fragment).commit();
            }
        });
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
            customLinearLayout.getLayoutParams().width = Utils.getScreenWidth(itemView.getContext()) / 2 - 4;
        }
    }
}
