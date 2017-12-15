package com.qemasoft.alhabibshop.app.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.model.Reviews;

import java.util.List;

/**
 * Created by Inzimam on 17-Oct-17.
 */

public class ProductReviewsAdapter extends RecyclerView.Adapter<ProductReviewsAdapter.MyViewHolder> {

    private List<Reviews> dataList;
    private Context context;
    private Utils utils;

    public ProductReviewsAdapter(List<Reviews> dataList) {
        this.dataList = dataList;
        Log.e("Constructor", "Working");
        Log.e("Constructor", "DataList Size = " + dataList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        this.utils = new Utils(context);

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_pro_author_review, parent, false);

        utils.printLog("LayoutInflated", "Working");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        utils.printLog("OnBIndMethod", "OnBind Working");
        final int pos = holder.getAdapterPosition();
        final Reviews data = dataList.get(pos);

        String orderId = data.getReviewId();

        holder.revAuthorName.setText(data.getRevAuthorName());
        holder.postedDate.setText(data.getRevPostDate());
        holder.revComment.setText(data.getReviewComment());
        holder.authorRating.setRating(Float.parseFloat(data.getAuthorRating()));

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView revAuthorName, postedDate, revComment;
        public RatingBar authorRating;

        public MyViewHolder(View itemView) {
            super(itemView);

            revAuthorName = itemView.findViewById(R.id.author_name_tv);
            postedDate = itemView.findViewById(R.id.post_date_tv);
            revComment = itemView.findViewById(R.id.review_comment_tv);
            authorRating = itemView.findViewById(R.id.author_rating_bar);

            utils.printLog("FindViewById", "Working");
        }
    }
}
