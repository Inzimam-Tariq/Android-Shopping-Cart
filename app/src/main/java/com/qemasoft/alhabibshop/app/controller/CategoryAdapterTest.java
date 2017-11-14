package com.qemasoft.alhabibshop.app.controller;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.model.MyCategory;
import com.qemasoft.alhabibshop.app.model.MyItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE;
import static com.qemasoft.alhabibshop.app.AppConstants.findStringByName;
import static com.qemasoft.alhabibshop.app.AppConstants.getHomeExtra;


/**
 * Created by Inzimam on 17-Oct-17.
 */

public class CategoryAdapterTest extends RecyclerView.Adapter<CategoryAdapterTest.MyViewHolder> {

    private List<String> keysStr;
    private CategoryAdapter categoryAdapter;
    private List<MyCategory> myCategoryList;
    private List<Integer> myCategoryImagesList = new ArrayList<>();
    private boolean isCategory = true;
    // Items
    private ItemAdapter itemAdapter;
    private List<MyItem> myItemList;
    private List<List<MyItem>> myItemListOfList;

    private Context context;
    private Utils utils;


    public CategoryAdapterTest(List<String> list) {
        this.keysStr = list;
        this.isCategory = true;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_main_frag_categories, parent, false);

        this.context = parent.getContext();
        this.utils = new Utils(context);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (keysStr.get(position).equals(findStringByName("fcategory"))) {
            holder.categoryTitle.setText(findStringByName("fcategory"));
        } else {
            holder.categoryTitle.setText(findStringByName(keysStr.get(position)));
        }
        if (myCategoryList.size() > 0 && isCategory) {
            categoryAdapter = new CategoryAdapter(myCategoryList);
            holder.recyclerView.setAdapter(categoryAdapter);
            isCategory = false;
        } else {
            if (myItemListOfList.size() > 0) {
                if (myCategoryList.size() > 0 && position > 0) {
                    itemAdapter = new ItemAdapter(myItemListOfList.get(position - 1));
                    holder.recyclerView.setAdapter(itemAdapter);
                } else {
                    itemAdapter = new ItemAdapter(myItemListOfList.get(position));
                    holder.recyclerView.setAdapter(itemAdapter);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return keysStr.size();
    }

    private void prepareData() {

        myItemListOfList = new ArrayList<>();
        myCategoryList = new ArrayList<>();

        String responseStr = getHomeExtra();
        try {
            JSONObject responseObject = new JSONObject(responseStr);
            Log.e("JSON_Response", "" + responseObject);
            boolean success = responseObject.optBoolean("success");
            if (success) {
                JSONObject homeObject = responseObject.optJSONObject("home");
                JSONObject modules = homeObject.optJSONObject("modules");

                for (int a = 0; a < keysStr.size(); a++) {
                    Log.e("KeyStr = ", keysStr.get(a));
                    if (keysStr.get(a).equals("fcategory")) {
                        Log.e("KeyStr = ", keysStr.get(a));
                        JSONArray featuredCategories = modules.optJSONArray(keysStr.get(a));
                        for (int i = 0; i < featuredCategories.length(); i++) {
                            JSONObject categoryObject = featuredCategories.getJSONObject(i);
                            MyCategory myCategory = new MyCategory(categoryObject.optString("category_id"),
                                    categoryObject.optString("name"), categoryObject.optString("icon"));
                            myCategoryList.add(myCategory);
                        }
                    } else {
                        myItemList = new ArrayList<>();
                        JSONArray featuredProduct = modules.optJSONArray(keysStr.get(a));
                        for (int i = 0; i < featuredProduct.length(); i++) {
                            JSONObject productObj = featuredProduct.getJSONObject(i);
                            MyItem myItem = new MyItem(productObj.optString("product_id"),
                                    productObj.optString("name"), productObj.optString("dis"),
                                    productObj.optString("price"), productObj.optString("thumb"));
                            myItemList.add(myItem);
                        }
                        myItemListOfList.add(myItemList);
                    }
                }
            } else {
                Log.e("SuccessFalse", "Within getCategories");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSONEx_CatAdapterTest", responseStr);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView categoryTitle;
        public RecyclerView recyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);

            categoryTitle = itemView.findViewById(R.id.category_title_tv);
            recyclerView = itemView.findViewById(R.id.main_cat_recycler_view);
            Log.e("Screen Width = ", "" + Utils.getScreenWidth(context));
            String toastMsg;
            int screenSize = utils.getScreenSize();
            switch (screenSize) {
                case SCREENLAYOUT_SIZE_LARGE:
                    toastMsg = "Large screen";
                    break;
                case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                    toastMsg = "Normal screen";
                    break;
                case Configuration.SCREENLAYOUT_SIZE_SMALL:
                    toastMsg = "Small screen";
                    break;
                default:
                    toastMsg = "Screen size is neither large, normal or small";
            }
//            Toast.makeText(context, toastMsg, Toast.LENGTH_LONG).show();

            RecyclerView.LayoutManager mLayoutManagerCat =
                    new GridLayoutManager(itemView.getContext(), 2, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(mLayoutManagerCat);

            recyclerView.setItemAnimator(new DefaultItemAnimator());
            prepareData();
        }
    }
}
