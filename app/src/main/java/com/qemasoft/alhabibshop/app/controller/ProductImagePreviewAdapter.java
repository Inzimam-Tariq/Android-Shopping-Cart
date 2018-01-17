package com.qemasoft.alhabibshop.app.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Inzimam on 17-Oct-17.
 */

public class ProductImagePreviewAdapter extends RecyclerView.Adapter<ProductImagePreviewAdapter.MyViewHolder> {

    private List<String> dataList;
    private Context context;
    private Utils utils;
    private ImageView previewIV;
    private ProgressBar progressBar;

    public ProductImagePreviewAdapter(ImageView previewIV,
                                      ProgressBar progressBar,
                                      List<String> dataList) {
        this.dataList = dataList;
        this.previewIV = previewIV;
        this.progressBar = progressBar;
        Log.e("Constructor", "Working");
        Log.e("Constructor", "DataList Size = " + dataList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        this.utils = new Utils(context);

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_product_image_preview, parent, false);

        utils.printLog("LayoutInflated", "Working");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        utils.printLog("OnBIndMethod", "OnBind Working");
        final int pos = holder.getAdapterPosition();
        final String imgPath = dataList.get(pos);

        if (pos == 0)
            setDefaultImage(imgPath, pos);

        if (!imgPath.isEmpty())
            Picasso.with(context)
                    .load(imgPath)
                    .noFade()
                    .resize(100, 100)
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            holder.progressBar.setVisibility(View.GONE);
                            holder.imageView.setImageResource(R.drawable.ic_close_black);
                        }
                    });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(context)
                        .load(imgPath)
                        .noFade()
//                        .resize(480, 170)
                        .into(previewIV, new Callback() {
                            @Override
                            public void onSuccess() {
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                progressBar.setVisibility(View.GONE);
                                previewIV.setImageResource(R.drawable.ic_close_black);
                            }
                        });
            }
        });

    }

    private void setDefaultImage(String imgPath, int pos) {
        if (!imgPath.isEmpty())
            Picasso.with(context)
                    .load(imgPath)
                    .noFade()
//                    .resize(480, 170)
                    .into(previewIV, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            progressBar.setVisibility(View.GONE);
                            previewIV.setImageResource(R.drawable.ic_close_black);
                        }
                    });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view);
            progressBar = itemView.findViewById(R.id.progress_bar);

            utils.printLog("FindViewById", "Working");
        }
    }
}
