package com.qemasoft.alhabibshop.app.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.model.MyCategory;

import java.util.List;


/**
 * Created by Inzimam on 17-Oct-17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private List<MyCategory> dataList;

    public CategoryAdapter(List<MyCategory> dataList) {
        this.dataList = dataList;
//        Log.e("Constructor", "Working");
//        Log.e("Constructor", "DataList Size = " + dataList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_category, parent, false);
//        Log.e("LayoutInflated", "Working");

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        Log.e("OnBIndMethod", "OnBind Working");
        MyCategory data = dataList.get(position);
//        holder.itemId.setText(data.getQuestionId());
        holder.categoryTitle.setText(data.getCategoryTitle());
        holder.categoryImage.setImageResource(data.getCatImage());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView categoryId, categoryTitle;
        public ImageView categoryImage;
        public LinearLayout customLinearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            categoryImage = (ImageView) itemView.findViewById(R.id.cat_img);
            categoryTitle = (TextView) itemView.findViewById(R.id.cat_title);
//            customLinearLayout = (LinearLayout) itemView.findViewById(R.id.custom_item_layout);
//            customLinearLayout.getLayoutParams().width = (int) (Utils.getScreenWidth(itemView.getContext()) / 2);
//            customLinearLayout.getLayoutParams().height = (int) (Utils.getScreenWidth(itemView.getContext()) / 2);
//            Log.e("FindViewById", "Working");
        }
    }
}
