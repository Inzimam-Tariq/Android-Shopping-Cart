package com.qemasoft.alhabibshop.controller;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qemasoft.alhabibshop.model.MenuSubCategory;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import hostflippa.com.opencart_android.R;

/**
 * Created by Inzimam on 29-Oct-17.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private List<String> dataListHeader;
    private HashMap<String, List<MenuSubCategory>> listHashMap;
    private boolean isRight, isLoggedIn;
    private List<Integer> userMenuIcons;

    public ExpandableListAdapter(List<String> dataListHeader,
                                 HashMap<String, List<MenuSubCategory>> listHashMap,
                                 boolean isRight, boolean isLoggedIn,
                                 List<Integer> userMenuIcons) {

        this.dataListHeader = dataListHeader;
        this.listHashMap = listHashMap;
        this.isRight = isRight;
        this.isLoggedIn = isLoggedIn;
        this.userMenuIcons = userMenuIcons;
    }

    @Override
    public int getGroupCount() {
        return dataListHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listHashMap.get(dataListHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dataListHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(dataListHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        View groupView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_group, parent, false);

        int iconPosition = groupPosition + 1;

        TextView lblListHeader = groupView.findViewById(R.id.lblListHeader);
        lblListHeader.setText(headerTitle);
//            lblListHeader.setTypeface(null, Typeface.BOLD);
        ImageView imageView = groupView.findViewById(R.id.imageView);
        if (isRight) {
            if (isLoggedIn) {
                imageView.setImageResource(userMenuIcons.get(groupPosition));
            }
        } else {
            Picasso.with(parent.getContext()).load(
                    "http://www.opencartgulf.com/image/catalog/icons/"
                            + iconPosition + ".png").into(imageView);
        }

        if (getChildrenCount(groupPosition) > 0) {
            lblListHeader.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0, isExpanded ? R.drawable.ic_menu_less : R.drawable.ic_menu_more, 0);
        }

        return groupView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {

        MenuSubCategory textChild = (MenuSubCategory) getChild(groupPosition, childPosition);

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        TextView lblListChild = itemView.findViewById(R.id.lblListItem);
        lblListChild.setText(textChild.getMenuSubCategoryName());
        Log.e("ChildText", textChild.getMenuSubCategoryName());

        return itemView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
