package com.qemasoft.alhabibshop.app.controller;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.model.UserSubMenu;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Inzimam on 29-Oct-17.
 */

public class ExpandableListAdapterRight extends BaseExpandableListAdapter {

    private List<String> dataListHeader;
    private HashMap<String, List<UserSubMenu>> listHashMap;
    private List<Integer> userMenuIcons;
    private Utils utils;

    public ExpandableListAdapterRight(List<String> dataListHeader,
                                      HashMap<String, List<UserSubMenu>> listHashMap,
                                      List<Integer> userMenuIcons) {

        this.dataListHeader = dataListHeader;
        this.listHashMap = listHashMap;
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
        utils = new Utils(parent.getContext());
        String headerTitle = (String) getGroup(groupPosition);
        View groupView;
        if (getChildrenCount(groupPosition) == 1) {
            groupView = new View(parent.getContext());
        } else {
            groupView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_group, parent, false);

            TextView lblListHeader = groupView.findViewById(R.id.lblListHeader);
            lblListHeader.setText(headerTitle);
            ImageView imageView = groupView.findViewById(R.id.imageView);
            ImageView expandCollapseImg = groupView.findViewById(R.id.expand_collapse_image);

            imageView.setImageResource(userMenuIcons.get(groupPosition));

            if (getChildrenCount(groupPosition) > 1
                    || groupPosition == userMenuIcons.size() - 1) {
                expandCollapseImg.setImageResource(isExpanded ? R.drawable.ic_expand_less_black : R.drawable.ic_expand_more_black);
//                lblListHeader.setCompoundDrawablesWithIntrinsicBounds(
//                        0, 0, isExpanded ? R.drawable.ic_expand_less_black : R.drawable.ic_expand_more_black, 0);
            }
        }

        return groupView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {

        View itemView;
        UserSubMenu userSubMenu = (UserSubMenu) getChild(groupPosition, childPosition);

        if (getChildrenCount(groupPosition) > 1
                || groupPosition == userMenuIcons.size() - 1) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);


            TextView lblListChild = itemView.findViewById(R.id.lblListItem);
            lblListChild.setText(userSubMenu.getUserSubMenuSymbolLeft().concat(" ")
                    .concat(userSubMenu.getUserSubMenuSymbolRight()).concat(" ")
                    .concat(userSubMenu.getUserSubMenuTitle())

            );

            try {
                utils.printLog("Bitmap image = " + userSubMenu.getFlagImage());
                lblListChild.setCompoundDrawables(
                        Utils.drawableFromUrl(
                                !userSubMenu.getFlagImage().isEmpty() ? userSubMenu.getFlagImage() : null),
                        null, null, null);
            } catch (IOException e) {
                e.printStackTrace();
                utils.printLog("Error Loading Bitmap Image");
            }
        } else {
            itemView = new View(parent.getContext());
        }
        Log.e("ChildText", userSubMenu.getUserSubMenuTitle());

        return itemView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
