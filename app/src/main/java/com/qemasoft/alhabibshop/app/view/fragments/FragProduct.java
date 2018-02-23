package com.qemasoft.alhabibshop.app.view.fragments;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.Preferences;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.controller.ExpandableListAdapterCategory;
import com.qemasoft.alhabibshop.app.controller.ItemAdapter;
import com.qemasoft.alhabibshop.app.controller.SubCatAdapter;
import com.qemasoft.alhabibshop.app.model.MenuCategory;
import com.qemasoft.alhabibshop.app.model.MenuSubCategory;
import com.qemasoft.alhabibshop.app.model.MyCategory;
import com.qemasoft.alhabibshop.app.model.MyItem;
import com.qemasoft.alhabibshop.app.view.activities.FetchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qemasoft.alhabibshop.app.AppConstants.ACCENT_COLOR;
import static com.qemasoft.alhabibshop.app.AppConstants.LEFT;
import static com.qemasoft.alhabibshop.app.AppConstants.PRIMARY_COLOR;
import static com.qemasoft.alhabibshop.app.AppConstants.PRODUCT_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.THEME_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragProduct extends MyBaseFragment implements View.OnClickListener {
    
    //    private ImageView backBannerIV;
//    private ProgressBar progressBar;
    private RelativeLayout filterLayout, sortLayout;
    private SubCatAdapter subCatAdapter;
    private RecyclerView subCatRecycleView;
    private ItemAdapter itemAdapter;
    private List<MyItem> myItemList;
    private TextView productTitleTV, filterTV, sortTV;
    private int selectedIndex;
    private String sortType;
    private String sortOrder;
    private Bundle bundle;
    private List<String> selectedFilters;
    
    private List<MenuCategory> headerList;
    private HashMap<MenuCategory, List<MenuSubCategory>> hashMap;
    
    
    public FragProduct() {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_products, container, false);
        initViews(view);
        initUtils();
        myItemList = new ArrayList<>();
        
        bundle = getArguments();
        if (bundle != null) {
            requestData(bundle.getString("id"));
        }
        
        filterLayout.setOnClickListener(this);
        sortLayout.setOnClickListener(this);
        utils.setCompoundDrawable(filterTV, LEFT, R.drawable.ic_filter_black);
        utils.setCompoundDrawable(sortTV, LEFT, R.drawable.ic_sort_black);
        
        return view;
    }
    
    private void requestData(String id) {
        
        bundle = getArguments();
        String from;
        if (bundle != null) {
            from = bundle.getString("from", "");
            Intent intent = new Intent(getContext(), FetchData.class);
            Map<String, String> map = new HashMap<>();
            utils.printLog("From = " + from + "\tId = " + id);
            utils.printLog("IsCodeWorking = Yes");
            utils.printLog("sortType=" + bundle
                    .getString("sortType"));
            utils.printLog("sortOrder=" + bundle
                    .getString("sortOrder"));
            if (bundle.getBoolean("hasSortFilter", false)) {
                
                map.put("sort", bundle.getString("sortType", ""));
                map.put("order", bundle.getString("sortOrder", ""));
            }
            if (bundle.containsKey("filter")) {
                utils.printLog("FilterOnCreate", bundle.getString("filter", ""));
                map.put("filter", bundle.getString("filter", ""));
            }
            if (from.contains("fromSearch")) {
                AppConstants.setMidFixApi("searchProduct");
                map.put("search", id);
                bundle.putBoolean("hasParameters", true);
                bundle.putSerializable("parameters", (Serializable) map);
                utils.printLog("Within Search = " + from);
            } else if (from.contains("mainActivity")) {
                AppConstants.setMidFixApi("getSpecialProducts");
                utils.printLog("Within Special Products = " + from);
            } else {
                utils.printLog("Within Else = " + from);
                AppConstants.setMidFixApi("products");
                map.put("category_id", id);
                bundle.putBoolean("hasParameters", true);
                bundle.putSerializable("parameters", (Serializable) map);
            }
            intent.putExtras(bundle);
            startActivityForResult(intent, PRODUCT_REQUEST_CODE);
            utils.printLog("Execution Completed = " + from);
        }
    }
    
    private void initViews(View view) {

//        backBannerIV = view.findViewById(R.id.image_view);
//        progressBar = view.findViewById(R.id.progress_bar);
        filterLayout = view.findViewById(R.id.filter_layout);
        sortLayout = view.findViewById(R.id.sort_layout);
        productTitleTV = view.findViewById(R.id.category_title_tv);
        subCatRecycleView = view.findViewById(R.id.sub_cat_recycle_view);
        
        mRecyclerView = view.findViewById(R.id.product_img_recycler_view);
        filterTV = view.findViewById(R.id.filter_tv);
        sortTV = view.findViewById(R.id.sort_tv);
        
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PRODUCT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    utils.printLog("Inside Res Frag Products = ");
                    final JSONObject response = new JSONObject(data.getStringExtra("result"));
                    
                    String categoryName = response.optString("category_name");
                    productTitleTV.setText(categoryName);
                    
                    JSONArray categories = response.optJSONArray("categories");
                    JSONArray products = response.optJSONArray("products");
                    
                    if ((categories == null || categories.toString().isEmpty())
                            && (products == null || products.toString().isEmpty())) {
                        utils.showAlert(R.string.information_text, R.string.no_data,
                                false,
                                R.string.ok, null,
                                R.string.cancel_text, null);
                        return;
                    }
                    
                    List<MyCategory> categoryList = new ArrayList<>();
                    if (categories != null) {
                        
                        productTitleTV.setVisibility(View.VISIBLE);
                        
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = categories.optJSONObject(i);
                            MyCategory category = new MyCategory(
                                    catObj.optString("category_id"),
                                    catObj.optString("name"));
                            categoryList.add(category);
                        }
                        
                        RecyclerView.LayoutManager mLayoutManagerCat =
                                new LinearLayoutManager(context,
                                        LinearLayoutManager.VERTICAL, false);
                        subCatRecycleView.setLayoutManager(mLayoutManagerCat);
                        subCatRecycleView.setItemAnimator(new DefaultItemAnimator());
                        
                        subCatAdapter = new SubCatAdapter(categoryList);
                        subCatRecycleView.setAdapter(subCatAdapter);
                    }
                    
                    utils.printLog("Products", products.toString());
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject productObj = products.optJSONObject(i);
                        MyItem item = new MyItem(productObj.optString("product_id")
                                , productObj.optString("name"), productObj.optString("special")
                                , productObj.optString("price"), productObj.optString("image"));
                        myItemList.add(item);
                    }
                    itemAdapter = new ItemAdapter(myItemList);
                    RecyclerView.LayoutManager mLayoutManager =
                            new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    
                    mRecyclerView.setAdapter(itemAdapter);
                    
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == AppConstants.FORCE_CANCELED) {
                try {
                    JSONObject response = new JSONObject(data.getStringExtra("result"));
                    String error = response.optString("error");
                    if (!error.isEmpty()) {
                        utils.showAlert(R.string.information_text, error,
                                false,
                                R.string.ok, null,
                                R.string.cancel_text, null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                utils.showAlert(R.string.an_error, R.string.error_fetching_data,
                        false,
                        R.string.ok, null,
                        R.string.cancel_text, null);
            }
        }
    }
    
    
    @Override
    public void onClick(View v) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        AlertDialog dialog;
        switch (v.getId()) {
            
            case R.id.sort_layout:
                List<String> filterList = Arrays.asList(context.getResources()
                        .getStringArray(R.array.sort_array));
                
                
                builder.setTitle(R.string.soft_by);
                builder.setSingleChoiceItems(filterList.toArray(new String[filterList.size()]),
                        selectedIndex, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedIndex = which;
                                utils.printLog("Index = " + selectedIndex);
                                
                                if (selectedIndex == 1) {
                                    sortOrder = "ASC";
                                    sortType = "pd.name";
                                } else if (selectedIndex == 2) {
                                    sortOrder = "DESC";
                                    sortType = "pd.name";
                                } else if (selectedIndex == 3) {
                                    sortOrder = "ASC";
                                    sortType = "p.price";
                                } else if (selectedIndex == 4) {
                                    sortOrder = "DESC";
                                    sortType = "p.price";
                                } else if (selectedIndex == 5) {
                                    sortOrder = "ASC";
                                    sortType = "p.model";
                                } else if (selectedIndex == 6) {
                                    sortOrder = "DESC";
                                    sortType = "p.model";
                                } else {
                                    selectedIndex = 0;
                                }
                            }
                        });
                builder.setPositiveButton(R.string.sort_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bundle bundle = getArguments();
                        if (getArguments() != null) {
                            if (selectedIndex != 0)
                                bundle.putBoolean("hasSortFilter", true);
                            bundle.putString("sortType", sortType);
                            bundle.putString("sortOrder", sortOrder);
                            utils.printLog("BeforeSwitching SortType = " + sortType
                                    + "SortOrder = " + sortOrder);
                            utils.switchFragment(new FragProduct(), bundle);
                        }
                    }
                });
                builder.setNegativeButton(R.string.cancel_text, null);
                dialog = builder.create();
                dialog.show();
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(40, 0, 40, 0);
                params.weight = 1f;
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setLayoutParams(params);
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setLayoutParams(params);
                
                String theme = Preferences.getSharedPreferenceString(appContext,
                        THEME_CODE, "default");
                if (theme != null && !theme.isEmpty() &&
                        !theme.equalsIgnoreCase("default")) {
                    String pColor = Preferences.getSharedPreferenceString(
                            appContext, PRIMARY_COLOR, "#EC7625");
                    String aColor = Preferences.getSharedPreferenceString(
                            appContext, ACCENT_COLOR, "#555555");
                    
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                            .setBackgroundColor(Color.parseColor(pColor));
                    dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                            .setBackgroundColor(Color.parseColor(aColor));
                } else {
                    String pColor = "#EC7625";
                    String aColor = "#555555";
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                            .setBackgroundColor(Color.parseColor(pColor));
                    dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                            .setBackgroundColor(Color.parseColor(aColor));
                }
                break;
            case R.id.filter_layout:
                if (headerList == null || headerList.isEmpty() || headerList.size() < 1) {
                    utils.showAlert(R.string.filter_text, R.string.no_filter,
                            false,
                            R.string.ok, null,
                            R.string.cancel_text, null);
                    return;
                }
                selectedFilters = new ArrayList<>();
                builder.setTitle(R.string.filter_text);
                
                ExpandableListView myList = new ExpandableListView(context);
                ExpandableListAdapterCategory myAdapter = new ExpandableListAdapterCategory(
                        headerList, hashMap, true);
                myList.setAdapter(myAdapter);
                myList.setGroupIndicator(null);
                
                builder.setView(myList);
                myList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(final ExpandableListView parent, View v,
                                                final int groupPosition, final int childPosition, long id) {
                        MenuSubCategory subCategory = (MenuSubCategory) parent.getExpandableListAdapter()
                                .getChild(groupPosition, childPosition);
                        CheckBox checkBox = (CheckBox) v;
                        checkBox.toggle();
                        utils.printLog("IsChecked = " + ((CheckBox) v).isChecked());
                        if (((CheckBox) v).isChecked()) {
                            utils.printLog("SubCatListItemId = " + subCategory.getMenuSubCategoryId());
                            selectedFilters.add(subCategory.getMenuSubCategoryId());
                        } else {
                            String filterId = subCategory.getMenuSubCategoryId();
                            if (!filterId.isEmpty())
                                selectedFilters.remove(filterId);
                        }
                        return false;
                    }
                });
                for (int i = 0; i < headerList.size(); i++) {
                    myList.expandGroup(i);
                }
                myList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                        return true;
                    }
                });
                
                builder.setPositiveButton(R.string.filter_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bundle bundle = getArguments();
                        if (getArguments() != null) {
                            StringBuilder filter = new StringBuilder();
                            if (selectedFilters.size() > 0) {
                                for (int i = 0; i < selectedFilters.size(); i++) {
                                    if (0 == i)
                                        filter.append(selectedFilters.get(i));
                                    else filter.append(",").append(selectedFilters.get(i));
                                }
                                String f = filter.toString();
                                bundle.putString("filter", f);
                                utils.printLog("Filter", f);
                                utils.switchFragment(new FragProduct(), bundle);
                            }
                        }
                    }
                });
                builder.setNegativeButton(R.string.cancel_text, null);
                dialog = builder.create();
                dialog.show();
                break;
            default:
                
                break;
        }
        
    }
    
    private void initFilter(JSONObject response) {
        headerList = new ArrayList<>();
        hashMap = new HashMap<>();
        
        try {
            JSONArray menuCategories = response.optJSONArray("filters");
            if (menuCategories == null || menuCategories.toString().isEmpty()) {
                return;
            }
            utils.printLog("FilterCat", menuCategories.toString());
            
            for (int i = 0; i < menuCategories.length(); i++) {
                
                JSONObject menuCategoryObj = menuCategories.getJSONObject(i);
                JSONArray menuSubCategoryArray = menuCategoryObj.optJSONArray(
                        "filter");
                
                List<MenuSubCategory> childMenuList = new ArrayList<>();
                for (int j = 0; j < menuSubCategoryArray.length(); j++) {
                    JSONObject menuSubCategoryObj = menuSubCategoryArray.getJSONObject(j);
                    MenuSubCategory menuSubCategory = new MenuSubCategory(
                            menuSubCategoryObj.optString("filter_id"),
                            menuSubCategoryObj.optString("name"));
                    childMenuList.add(menuSubCategory);
                }
                MenuCategory menuCategory = new MenuCategory(menuCategoryObj.optString(
                        "filter_group_id"),
                        menuCategoryObj.optString("name"),
                        menuCategoryObj.optString("icon"),
                        childMenuList);
                headerList.add(menuCategory);
                hashMap.put(headerList.get(i), menuCategory.getMenuSubCategory());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
    }
    
}
