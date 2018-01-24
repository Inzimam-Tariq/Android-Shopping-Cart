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
import com.qemasoft.alhabibshop.app.Preferences;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.model.MyCategory;
import com.qemasoft.alhabibshop.app.model.MyItem;
import com.qemasoft.alhabibshop.app.view.fragments.FragProduct;
import com.qemasoft.alhabibshop.app.view.fragments.FragProductDetail;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Inzimam on 17-Oct-17.
 */

public class SubCatAdapter extends RecyclerView.Adapter<SubCatAdapter.MyViewHolder> {

    private List<MyCategory> dataList;
    private Context context;
    private Utils utils;

    public SubCatAdapter(List<MyCategory> dataList) {

        this.dataList = dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        this.utils = new Utils(context);

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_sub_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        utils.printLog("OnBIndMethod", "OnBind Working");
        final MyCategory data = dataList.get(position);

        if (data != null) {
            holder.itemTitle.setText(data.getCategoryTitle());


            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putString("id", data.getCategoryId());
                    utils.printLog("categoryId", data.getCategoryId());

                    utils.switchFragment(new FragProduct(), bundle);
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
            itemTitle = itemView.findViewById(R.id.category_title_tv);
        }
    }
}
