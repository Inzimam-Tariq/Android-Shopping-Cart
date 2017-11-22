package com.qemasoft.alhabibshop.app.controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.R;

/**
 * Created by Inzimam on 22-Nov-17.
 */

public class ViewHolder2 extends RecyclerView.ViewHolder {

    private TextView title, description;

    public ViewHolder2(View v) {
        super(v);
        title = v.findViewById(R.id.title_tv);
        description = v.findViewById(R.id.description_tv);
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getDescription() {
        return description;
    }

    public void setDescription(TextView description) {
        this.description = description;
    }
}