package com.qemasoft.alhabibshop.app.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.model.MyCategory;
import com.qemasoft.alhabibshop.app.view.fragments.FragProduct;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Inzimam on 17-Oct-17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    
    private List<MyCategory> dataList;
    private Context context;
    private Utils utils;
//    private boolean isPlainCategory;
    
    public CategoryAdapter(List<MyCategory> dataList) {
        this.dataList = dataList;
    }
    
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View categoryView;
        this.context = parent.getContext();
        this.utils = new Utils(context);
//        if (isPlainCategory) {
//            categoryView = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.layout_categories, parent, false);
//        } else {
        categoryView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_category, parent, false);
//        }
//        Log.e("LayoutInflated", "Working");
        
        return new MyViewHolder(categoryView);
    }
    
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
//        Log.e("OnBIndMethod", "OnBind Working");
        final MyCategory data = dataList.get(position);
        final String id = data.getCategoryId();
        holder.categoryTitle.setText(data.getCategoryTitle());
        String imgPath = data.getCatImage();
        if (!imgPath.isEmpty())
            Picasso.with(context).load(imgPath).into(holder.categoryImage, new Callback() {
                @Override
                public void onSuccess() {
                    holder.progressBar.setVisibility(View.GONE);
                }
                
                @Override
                public void onError() {
                    holder.progressBar.setVisibility(View.GONE);
                    holder.categoryImage.setImageResource(R.drawable.ic_close_black);
                }
            });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                Log.e("itemId", id);
                utils.switchFragment(new FragProduct(), bundle);
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return dataList.size();
    }
    
    public class MyViewHolder extends RecyclerView.ViewHolder {
        
        private TextView categoryId, categoryTitle;
        private ImageView categoryImage;
        private LinearLayout customLinearLayout;
        private ProgressBar progressBar;
        
        public MyViewHolder(View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.cat_img);
            categoryTitle = itemView.findViewById(R.id.cat_title);
            customLinearLayout = itemView.findViewById(R.id.cat_layout);
            progressBar = itemView.findViewById(R.id.progress_bar);
            int itemToShow = 2;
            int screenWidth = Utils.getScreenWidth(context);
            if (screenWidth <= 250) {
                itemToShow = 1;
            } else if (screenWidth > 480 && screenWidth <= 1280) {
                itemToShow = 3;
            } else if (screenWidth > 1280) {
                itemToShow = 4;
            }
            customLinearLayout.getLayoutParams().width = Utils.getScreenWidth(
                    itemView.getContext()) / itemToShow - 30;
//            customLinearLayout.getLayoutParams().height = Utils.getScreenWidth(itemView.getContext()) / 2;
//            Log.e("FindViewById", "Working");
        }
    }
}
