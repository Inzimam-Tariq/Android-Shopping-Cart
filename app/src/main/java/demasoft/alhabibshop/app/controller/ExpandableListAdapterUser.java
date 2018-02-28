package demasoft.alhabibshop.app.controller;

import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import demasoft.alhabibshop.app.R;
import demasoft.alhabibshop.app.model.UserSubMenu;
import demasoft.alhabibshop.app.utils.MyApp;
import demasoft.alhabibshop.app.utils.Preferences;
import demasoft.alhabibshop.app.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static demasoft.alhabibshop.app.utils.AppConstants.LANGUAGE_KEY;
import static demasoft.alhabibshop.app.utils.AppConstants.appContext;


/**
 * Created by Inzimam on 29-Oct-17.
 */

public class ExpandableListAdapterUser extends BaseExpandableListAdapter {
    
    private List<String> dataListHeader;
    private HashMap<String, List<UserSubMenu>> listHashMap;
    private List<Integer> userMenuIcons;
    private Utils utils;
    
    public ExpandableListAdapterUser(List<String> dataListHeader,
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
        View groupView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_group, parent, false);
        TextView lblListHeader = groupView.findViewById(R.id.lblListHeader);
        lblListHeader.setText(headerTitle);
        ImageView imageView = groupView.findViewById(R.id.imageView);
        ImageView expandCollapseImg = groupView.findViewById(R.id.expand_collapse_image);
        View divider = groupView.findViewById(R.id.header_divider);
        
        if (getChildrenCount(groupPosition) == 1) {
            groupView = new View(parent.getContext());
            
            divider.setVisibility(View.GONE);
            
        } else {
            
            imageView.setImageResource(userMenuIcons.get(groupPosition));
            
            if (getChildrenCount(groupPosition) > 1
                    || groupPosition == userMenuIcons.size() - 1) {
                expandCollapseImg.setImageResource(isExpanded ? R.drawable.ic_expand_less_black : R.drawable.ic_expand_more_black);
            }
        }
        
        
        return groupView;
    }
    
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        
        View itemView;
        final UserSubMenu userSubMenu = (UserSubMenu) getChild(groupPosition, childPosition);
        
        if (getChildrenCount(groupPosition) > 1
                || groupPosition == userMenuIcons.size() - 1) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            
            TextView childTV = itemView.findViewById(R.id.lblListItem);
            View divider = itemView.findViewById(R.id.child_divider);
            String dividerColor = Preferences.getSharedPreferenceString(appContext,
                    "divider_color", "");
            if (dividerColor != null && !dividerColor.isEmpty()) {
                divider.setBackgroundColor(Color.parseColor(dividerColor));
            }
            
            if (!userSubMenu.getFlagImage().isEmpty()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    childTV.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                }
                ImageView imageView = itemView.findViewById(R.id.image_view);
                imageView.setVisibility(View.VISIBLE);
                int val = childTV.getPaddingLeft();
                utils.printLog("ExpandableAdapter", "padding = " + val);
                String lang = Preferences
                        .getSharedPreferenceString(appContext, LANGUAGE_KEY, "ar");
                Locale l = new Locale(lang);
                Log.e("FragSlider", "IsRTL = " + MyApp.isRTLTrue(l));
                if (MyApp.isRTLTrue(l)) {
                    imageView.setPadding(0, 0, imageView.getPaddingRight(), 0);
                    childTV.setPadding(0, 0, 22, 0);
                } else {
                    imageView.setPadding(imageView.getPaddingRight(), 0, 0, 0);
                    childTV.setPadding(22, 0, 0, 0);
                }
                val = childTV.getPaddingLeft();
                utils.printLog("ExpandableAdapter", "padding After = " + val);
//                int v = android.R.attr.expandableListPreferredChildPaddingLeft;
                Picasso.with(imageView.getContext())
                        .load(userSubMenu.getFlagImage())
                        .error(R.drawable.ic_close_black)
                        .resize(40, 30)
                        .into(imageView);
            }
            
            
            childTV.setText(userSubMenu.getUserSubMenuSymbolLeft().concat(" ")
                    .concat(userSubMenu.getUserSubMenuSymbolRight()).concat(" ")
                    .concat(userSubMenu.getUserSubMenuTitle())
            
            );
            
        } else {
            itemView = new View(parent.getContext());
            itemView.setMinimumHeight(0);
        }
        Log.e("ChildText", userSubMenu.getUserSubMenuTitle());
        
        return itemView;
    }
    
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        
        return true;
    }
}
