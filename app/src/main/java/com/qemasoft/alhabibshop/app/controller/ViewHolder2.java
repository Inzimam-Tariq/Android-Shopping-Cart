package com.qemasoft.alhabibshop.app.controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.R;

/**
 * Created by Inzimam on 22-Nov-17.
 */

public class ViewHolder2 extends RecyclerView.ViewHolder {

    private TextView title, description;
    private ImageView imageView;
    private ProgressBar progressBar;

    public ViewHolder2(View v) {
        super(v);
//        title = v.findViewById(R.id.title_tv);
//        description = v.findViewById(R.id.description_tv);
        imageView = v.findViewById(R.id.disc_image_view);
        progressBar = v.findViewById(R.id.progress_bar);
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getDescription() {
        return description;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public ProgressBar getProgressBar() {

        return progressBar;
    }
}