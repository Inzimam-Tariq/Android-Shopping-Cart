package com.qemasoft.alhabibshop.app.controller;

import android.content.Context;
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
import com.qemasoft.alhabibshop.app.model.MyCategory;
import com.qemasoft.alhabibshop.app.view.fragments.FragCategories;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Inzimam on 17-Oct-17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private List<MyCategory> dataList;
    private Context context;

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
        this.context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        Log.e("OnBIndMethod", "OnBind Working");
        MyCategory data = dataList.get(position);
//        holder.itemId.setText(data.getQuestionId());
        holder.categoryTitle.setText(data.getCategoryTitle());
        Picasso.with(context).load(data.getCatImage()).into(holder.categoryImage);
//        holder.categoryImage.setImageResource(data.getCatImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            AppCompatActivity activity = (AppCompatActivity) context;

            @Override
            public void onClick(View v) {
                Fragment fragment = new FragCategories();
//        //           FragRegister fragRegister = new  FragRegister();
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

        public TextView categoryId, categoryTitle;
        public ImageView categoryImage;
        public LinearLayout customLinearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.cat_img);
            categoryTitle = itemView.findViewById(R.id.cat_title);
            customLinearLayout = itemView.findViewById(R.id.cat_layout);
            customLinearLayout.getLayoutParams().width = Utils.getScreenWidth(itemView.getContext()) / 2 - 20;
//            customLinearLayout.getLayoutParams().height = Utils.getScreenWidth(itemView.getContext()) / 2;
//            Log.e("FindViewById", "Working");
        }
    }
}