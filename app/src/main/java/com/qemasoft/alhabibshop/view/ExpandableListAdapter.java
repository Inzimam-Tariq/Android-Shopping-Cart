package com.qemasoft.alhabibshop.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;
import com.qemasoft.alhabibshop.model.MenuSubCategory;

import java.util.HashMap;
import java.util.List;

import hostflippa.com.opencart_android.R;

/**
 * Created by Inzimam on 29-Oct-17.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> dataListHeader;
    private HashMap<String, List<MenuSubCategory>> listHashMap;

    public ExpandableListAdapter(Context context, List<String> dataListHeader,
                                 HashMap<String, List<MenuSubCategory>> listHashMap) {
        this.context = context;
        this.dataListHeader = dataListHeader;
        this.listHashMap = listHashMap;
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
        ANImageView imageView = groupView.findViewById(R.id.imageView);
        imageView.setImageUrl("http://www.opencartgulf.com/image/catalog/icons/"
                + iconPosition + ".png");

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

        return itemView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
