package com.qemasoft.alhabibshop.app.controller;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.model.UserSubMenu;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;


/**
 * Created by Inzimam on 29-Oct-17.
 */

public class ExpandableListAdapterRight extends BaseExpandableListAdapter {

    private List<String> dataListHeader;
    private HashMap<String, List<UserSubMenu>> listHashMap;
    private boolean isRight;
    private List<Integer> userMenuIcons;

    public ExpandableListAdapterRight(List<String> dataListHeader,
                                      HashMap<String, List<UserSubMenu>> listHashMap,
                                      boolean isRight, List<Integer> userMenuIcons) {

        this.dataListHeader = dataListHeader;
        this.listHashMap = listHashMap;
        this.isRight = isRight;
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
            imageView.setImageResource(userMenuIcons.get(groupPosition));
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

        UserSubMenu userSubMenu = (UserSubMenu) getChild(groupPosition, childPosition);

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        TextView lblListChild = itemView.findViewById(R.id.lblListItem);
        lblListChild.setText(userSubMenu.getUserSubMenuSymbolLeft() + ""
                + userSubMenu.getUserSubMenuSymbolRight() + " "
                + userSubMenu.getUserSubMenuTitle());
        Log.e("ChildText", userSubMenu.getUserSubMenuTitle());

        return itemView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
