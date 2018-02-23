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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.MyApp;
import com.qemasoft.alhabibshop.app.Preferences;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.model.MyItem;
import com.qemasoft.alhabibshop.app.view.fragments.FragProductDetail;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import static com.qemasoft.alhabibshop.app.AppConstants.ACCENT_COLOR;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;


/**
 * Created by Inzimam on 17-Oct-17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    
    private List<MyItem> dataList;
    private Context context;
    private Utils utils;
    private String accentColor = Preferences.getSharedPreferenceString(
            appContext, ACCENT_COLOR, "#555555");
    
    public ItemAdapter(List<MyItem> dataList) {
        
        this.dataList = dataList;
//        Log.e("Constructor", "Working");
//        Log.e("Constructor", "DataList Size = " + dataList.size());
    }
    
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        
        this.context = parent.getContext();
        this.utils = new Utils(context);
        
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item, parent, false);
//        utils.printLog("LayoutInflated", "Working");
        
        return new MyViewHolder(itemView);
    }
    
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        utils.printLog("OnBIndMethod", "OnBind Working");
        final MyItem data = dataList.get(position);
        
        if (data != null) {
            holder.itemTitle.setText(data.getItemTitle());
            String imgPath = data.getItemImage();
            utils.printLog("ImagePath = " + imgPath);
            if (!imgPath.isEmpty())
                Picasso.with(context)
                        .load(imgPath)
                        .noFade()
                        .resize(250, 250)
                        .into(holder.img, new Callback() {
                            @Override
                            public void onSuccess() {
                                holder.progressBar.setVisibility(View.GONE);
                            }
                            
                            @Override
                            public void onError() {
                                holder.progressBar.setVisibility(View.GONE);
                                holder.img.setImageResource(R.drawable.ic_close_black);
                            }
                        });
//            Picasso.with(context).setIndicatorsEnabled(true);
            
            holder.img.getLayoutParams().height =
                    Utils.getScreenWidth(holder.img.getContext()) / 2 - 50;
            
            String symbol = Preferences.getSharedPreferenceString(context
                    , AppConstants.CURRENCY_SYMBOL_KEY, "$");
            
            TextView itemPriceTV = holder.itemPriceFull;
            if (!accentColor.isEmpty()) {
                utils.applyAccentColor(itemPriceTV);
            }
            TextView itemPriceSpecialTV = holder.itemPriceSpecial;
            utils.printLog("ItemAdapter", "IsRTL = " + MyApp.isRTL(Locale.getDefault()));
            
            if (!data.getItemPriceSpecial().isEmpty()) {
                itemPriceSpecialTV.setVisibility(View.VISIBLE);
                
                itemPriceSpecialTV.setText(symbol.concat("").concat(data.getItemPriceFull()));
                itemPriceTV.setText(symbol.concat("").concat(data.getItemPriceSpecial()));
                
                // set StrikeThrough to textView
                itemPriceSpecialTV.setPaintFlags(itemPriceSpecialTV.getPaintFlags()
                        | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                itemPriceTV.setText(symbol.concat("").concat(data.getItemPriceFull()));
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
        
        public TextView itemTitle, itemPriceFull, itemPriceSpecial;
        public LinearLayout customLinearLayout;
        private ImageView img;
        private ProgressBar progressBar;
        
        public MyViewHolder(View itemView) {
            
            super(itemView);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemPriceSpecial = itemView.findViewById(R.id.disc_price);
            itemPriceFull = itemView.findViewById(R.id.full_price);
            img = itemView.findViewById(R.id.img);
            progressBar = itemView.findViewById(R.id.progress_bar);
            customLinearLayout = itemView.findViewById(R.id.custom_item_layout);
            customLinearLayout.getLayoutParams().width = Utils.getScreenWidth(
                    itemView.getContext()) / 2 - 10;
        }
    }
}
